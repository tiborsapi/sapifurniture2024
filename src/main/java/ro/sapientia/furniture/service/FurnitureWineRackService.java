package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.FurnitureWineRack;
import ro.sapientia.furniture.repository.FurnitureWineRackRepository;

import java.util.List;

@Service
public class FurnitureWineRackService {

    private final FurnitureWineRackRepository furnitureWineRackRepository;

    public FurnitureWineRackService(final FurnitureWineRackRepository furnitureRackRepository) {
        this.furnitureWineRackRepository = furnitureRackRepository;
    }

    public List<FurnitureWineRack> findAllFurnitureWineRack(){
        return furnitureWineRackRepository.findAll();
    }

    public FurnitureWineRack findFurnitureWineRackById(Long id){
        return furnitureWineRackRepository.findFurnitureWineRacksById(id);
    }

    public List<FurnitureWineRack> findAllFurnitureWineRacksByCapacityBetween(Integer bottom, Integer top){
        return furnitureWineRackRepository.findAllFurnitureWineRackByCapacityBetween(bottom, top);
    }

    public List<FurnitureWineRack> findAllFurnitureWineRacksByCurrentLoadNumberBetween(Integer bottom, Integer top){
        return furnitureWineRackRepository.findAllFurnitureWineRackByCurrentLoadBetween(bottom, top);
    }

    public List<FurnitureWineRack> findAllFurnitureWineRacksByFrontView(Boolean frontView){
        return furnitureWineRackRepository.findAllFurnitureWineRackByTransparentFront(frontView);
    }

    public FurnitureWineRack saveFurnitureWineRack(FurnitureWineRack furnitureWineRack){
        return this.furnitureWineRackRepository.save(furnitureWineRack);
    }


}
