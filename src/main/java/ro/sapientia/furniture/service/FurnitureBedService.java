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

    public List<FurnitureBed> findAllBeds(){
        return furnitureBedRepository.findAll();
    }

    public FurnitureBed findBedById(Long id){
        return furnitureBedRepository.findBedById(id);
    }

    public List<FurnitureBed> findAllBedsByType(BedType type){
        return furnitureBedRepository.findBedsByType(type);
    }

    public List<FurnitureBed> findAllBedsByWood(WoodType wood){
        return furnitureBedRepository.findBedsByWood(wood);
    }

    public FurnitureBed saveBed(FurnitureBed furnitureBed){
        return this.furnitureBedRepository.save(furnitureBed);
    }
}
