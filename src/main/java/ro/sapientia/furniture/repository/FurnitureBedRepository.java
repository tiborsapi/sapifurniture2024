package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;

import java.util.ArrayList;

public interface FurnitureBedRepository extends JpaRepository<FurnitureBed, Long> {
    FurnitureBed findBedById(Long id);

    ArrayList<FurnitureBed> findBedsByType(BedType type);

    ArrayList<FurnitureBed> findBedsByWood(WoodType type);
}
