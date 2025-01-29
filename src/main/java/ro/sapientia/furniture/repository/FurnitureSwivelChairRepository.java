package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.FurnitureSwivelChair;

import java.util.ArrayList;

public interface FurnitureSwivelChairRepository extends JpaRepository<FurnitureSwivelChair, Long> {

    FurnitureSwivelChair findByFurnitureSwivelChairId(long id);

    ArrayList<FurnitureSwivelChair> findFurnitureSwivelChairByWeightCapacity(int weightCapacity);

}
