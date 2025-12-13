package ro.sapientia.furniture.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

@Service
public class PersistenceService {

    private final FurnitureBodyRepository furnitureBodyRepository;
    private final ComponentListRepository componentListRepository;
    private final RawMaterialService rawMaterialService;

    public PersistenceService(FurnitureBodyRepository furnitureBodyRepository,
                              ComponentListRepository componentListRepository,
                              RawMaterialService rawMaterialService) {
        this.furnitureBodyRepository = furnitureBodyRepository;
        this.componentListRepository = componentListRepository;
        this.rawMaterialService = rawMaterialService;
    }

    @Transactional
    public FurnitureBody saveFurnitureBodyAndComponents(FurnitureBody fb, Long createdByUserId) {
        // 1) Save furniture body (frontElements cascade = ALL -> frontElements mentése megtörténik)
        FurnitureBody savedFb = furnitureBodyRepository.save(fb);

        // 2) Készítsd el a komponenslistát a model metódusa alapján
        ComponentList cl = savedFb.getRawMaterials(); // ez új ComponentList(savedFb, lista)
        cl.setCreatedBy(createdByUserId);

        // 3) Minden RawMaterialhez állítsd be a componentList referenciát
        List<RawMaterial> candidateMaterials = cl.getRawMaterials();
        if (candidateMaterials == null) {
            candidateMaterials = new ArrayList<>();
            cl.setRawMaterials(candidateMaterials);
        } else {
            for (RawMaterial rm : candidateMaterials) {
                rm.setComponentList(cl);
            }
        }

        // 4) Mentsd először a ComponentList-et, hogy legyen ID-je (új raw materialokhoz FK-ként használható)
        ComponentList savedCl = componentListRepository.save(cl);

        // 5) A deduplikáló szolgáltatás segítségével mentsd a raw materialokat:
        //    - ha létezik: növeli a quantity-t és frissíti az updated_at-ot
        //    - ha nem létezik: létrehozza az új rekordot, a component_list_id-vel
        List<RawMaterial> persistedMaterials = new ArrayList<>();
        for (RawMaterial rm : candidateMaterials) {
            // ensure the RM references the persisted component list
            rm.setComponentList(savedCl);
            RawMaterial persisted = rawMaterialService.findOrCreateAndIncreaseQuantity(rm);
            persistedMaterials.add(persisted);
        }

        // 6) Frissítsük a component listában tárolt rawMaterial hivatkozásokat a perzisztált objektumokra
        savedCl.setRawMaterials(persistedMaterials);
        componentListRepository.save(savedCl);

        return savedFb;
    }
}
