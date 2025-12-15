package ro.sapientia.furniture.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.RawMaterialDTO;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.RawMaterialRepository;

@Service
public class ComponentListService {

    private final ComponentListRepository componentListRepository;
    private final FurnitureBodyRepository furnitureBodyRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final RawMaterialTypeService rawMaterialTypeService;
    private final RawMaterialService rawMaterialService;

    public ComponentListService(ComponentListRepository componentListRepository,
                                FurnitureBodyRepository furnitureBodyRepository,
                                RawMaterialRepository rawMaterialRepository,
                                RawMaterialTypeService rawMaterialTypeService,
                                RawMaterialService rawMaterialService) {
        this.componentListRepository = componentListRepository;
        this.furnitureBodyRepository = furnitureBodyRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.rawMaterialTypeService = rawMaterialTypeService;
        this.rawMaterialService = rawMaterialService;
    }

    @Transactional
    public ComponentList create(ComponentListDTO dto) {
        ComponentList cl = new ComponentList();
        if (dto.getCreatedBy() != null) {
            cl.setCreatedBy(dto.getCreatedBy());
        }

        if (dto.getFurnitureBodyId() != null) {
            FurnitureBody fb = furnitureBodyRepository.findFurnitureBodyById(dto.getFurnitureBodyId());
            cl.setFurnitureBody(fb);
        }

        // persist component list first so we have an id
        cl = componentListRepository.save(cl);

        List<RawMaterial> saved = new ArrayList<>();
        if (dto.getRawMaterials() != null) {
            for (RawMaterialDTO rmd : dto.getRawMaterials()) {
                RawMaterialType rmt = null;
                if (rmd.getRawMaterialTypeName() != null) {
                    rmt = rawMaterialTypeService.findOrCreateByName(rmd.getRawMaterialTypeName());
                }
                RawMaterial candidate = new RawMaterial(rmd.getHeight(), rmd.getWidth(), rmd.getLength(), rmt, rmd.getQuantity());
                // find or create and increase quantity
                RawMaterial persisted = rawMaterialService.findOrCreateAndIncreaseQuantity(candidate);
                persisted.setComponentList(cl);
                persisted = rawMaterialRepository.save(persisted);
                saved.add(persisted);
            }
        }

        cl.setRawMaterials(saved);
        return componentListRepository.save(cl);
    }

    @Transactional
    public Optional<ComponentList> update(Long id, ComponentListDTO dto) {
        return componentListRepository.findById(id).map(existing -> {
            if (dto.getFurnitureBodyId() != null) {
                FurnitureBody fb = furnitureBodyRepository.findFurnitureBodyById(dto.getFurnitureBodyId());
                existing.setFurnitureBody(fb);
            }
            
            // dissociate previous raw materials
            if (existing.getRawMaterials() != null) {
                for (RawMaterial rm : existing.getRawMaterials()) {
                    rm.setComponentList(null);
                    rawMaterialRepository.save(rm);
                }
            }

            List<RawMaterial> newRms = new ArrayList<>();
            if (dto.getRawMaterials() != null) {
                for (RawMaterialDTO rmd : dto.getRawMaterials()) {
                    RawMaterialType rmt = null;
                    if (rmd.getRawMaterialTypeName() != null) {
                        rmt = rawMaterialTypeService.findOrCreateByName(rmd.getRawMaterialTypeName());
                    }
                    RawMaterial candidate = new RawMaterial(rmd.getHeight(), rmd.getWidth(), rmd.getLength(), rmt, rmd.getQuantity());
                    RawMaterial persisted = rawMaterialService.findOrCreateAndIncreaseQuantity(candidate);
                    persisted.setComponentList(existing);
                    persisted = rawMaterialRepository.save(persisted);
                    newRms.add(persisted);
                }
            }
            existing.setRawMaterials(newRms);
            return componentListRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        return componentListRepository.findById(id).map(existing -> {
            if (existing.getRawMaterials() != null) {
                for (RawMaterial rm : existing.getRawMaterials()) {
                    rm.setComponentList(null);
                    rawMaterialRepository.save(rm);
                }
            }
            componentListRepository.delete(existing);
            return true;
        }).orElse(false);
    }
}
