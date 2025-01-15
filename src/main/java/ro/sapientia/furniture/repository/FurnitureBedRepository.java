package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;

import java.util.ArrayList;

public interface FurnitureBedRepository extends JpaRepository<FurnitureBed, Long> {
    FurnitureBed findFurnitureBedById(Long id);

    ArrayList<FurnitureBed> findFurnitureBedsByType(BedType type);

    ArrayList<FurnitureBed> findFurnitureBedsByWood(WoodType type);
}
