package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.FurnitureSwivelChair;

import java.util.ArrayList;

public interface FurnitureSwivelChairRepository extends JpaRepository<FurnitureSwivelChair, Long> {

    FurnitureSwivelChair findFurnitureSwivelChairById(Long id);

    ArrayList<FurnitureSwivelChair> findFurnitureSwivelChairByWeightCapacity(int weightCapacity);

    ArrayList<FurnitureSwivelChair> findFurnitureSwivelChairByMaterial(String material);

}
