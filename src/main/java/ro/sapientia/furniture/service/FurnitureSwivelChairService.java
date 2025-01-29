package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.FurnitureSwivelChair;
import ro.sapientia.furniture.repository.FurnitureSwivelChairRepository;

@Service
public class FurnitureSwivelChairService {

    private final FurnitureSwivelChairRepository furnitureSwivelChairRepository;

    public FurnitureSwivelChairService(final FurnitureSwivelChairRepository furnitureSwivelChairRepository) {
        this.furnitureSwivelChairRepository = furnitureSwivelChairRepository;
    }

    public List<FurnitureSwivelChair> findAllFurnitureSwivelChairs() {
        return furnitureSwivelChairRepository.findAll();
    }

    public FurnitureSwivelChair findFurnitureSwivelChairById(Long id) {
        return furnitureSwivelChairRepository.findFurnitureSwivelChairById(id);
    }

    public List<FurnitureSwivelChair> findAllFurnitureSwivelChairByWeightCapacity(int weightCapacity) {
        return furnitureSwivelChairRepository.findFurnitureSwivelChairByWeightCapacity(weightCapacity);
    }

    public FurnitureSwivelChair saveFurnitureSwivelChair(FurnitureSwivelChair furnitureSwivelChair) {
        return this.furnitureSwivelChairRepository.save(furnitureSwivelChair);
    }
}
