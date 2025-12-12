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

    public List<FurnitureSwivelChair> findAllFurnitureSwivelChairByMaterial(String material) {
        return furnitureSwivelChairRepository.findFurnitureSwivelChairByMaterial(material);
    }

    public FurnitureSwivelChair create(FurnitureSwivelChair furnitureSwivelChair) {
        return this.furnitureSwivelChairRepository.saveAndFlush(furnitureSwivelChair);
    }

    public FurnitureSwivelChair update(FurnitureSwivelChair furnitureSwivelChair) {
        return this.furnitureSwivelChairRepository.saveAndFlush(furnitureSwivelChair);
    }

    public void delete(Long id) {
        this.furnitureSwivelChairRepository.deleteById(id);
    }
}
