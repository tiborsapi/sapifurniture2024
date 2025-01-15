package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;
import ro.sapientia.furniture.repository.FurnitureBedRepository;

import java.util.List;

@Service
public class FurnitureBedService {
    private final FurnitureBedRepository furnitureBedRepository;

    public FurnitureBedService(final FurnitureBedRepository furnitureBedRepository) {
        this.furnitureBedRepository = furnitureBedRepository;
    }

    public List<FurnitureBed> findAllFurnitureBeds(){
        return furnitureBedRepository.findAll();
    }

    public FurnitureBed findFurnitureBedById(Long id){
        return furnitureBedRepository.findFurnitureBedById(id);
    }

    public List<FurnitureBed> findAllFurnitureBedsByType(BedType type){
        return furnitureBedRepository.findFurnitureBedsByType(type);
    }

    public List<FurnitureBed> findAllFurnitureBedsByWood(WoodType wood){
        return furnitureBedRepository.findFurnitureBedsByWood(wood);
    }

    public FurnitureBed saveFurnitureBed(FurnitureBed furnitureBed){
        return this.furnitureBedRepository.save(furnitureBed);
    }
}
