package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.RockingChairModel;

import java.util.ArrayList;

public interface RockingChairRepository extends JpaRepository<RockingChairModel, Long> {

    ArrayList<RockingChairModel> findRockingChairById(Long id);

    ArrayList<RockingChairModel> findRockingChairByMaterial(String material);

    ArrayList<RockingChairModel> findAllRockingChairModelByRockerRadiusBetween(Double minAngle, Double maxAngle);

}