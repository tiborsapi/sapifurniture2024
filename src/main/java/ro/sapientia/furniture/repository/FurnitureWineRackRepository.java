package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.sapientia.furniture.model.FurnitureWineRack;

import java.util.ArrayList;

public interface FurnitureWineRackRepository extends JpaRepository<FurnitureWineRack, Long> {

    FurnitureWineRack findFurnitureWineRacksById(Long id);

    ArrayList<FurnitureWineRack> findAllFurnitureWineRackByCapacityBetween(Integer capacity1, Integer capacity2);

    ArrayList<FurnitureWineRack> findAllFurnitureWineRackByCurrentLoadBetween(Integer currentLoad1, Integer currentLoad2);

    ArrayList<FurnitureWineRack> findAllFurnitureWineRackByTransparentFront(Boolean transparentFront);

}
