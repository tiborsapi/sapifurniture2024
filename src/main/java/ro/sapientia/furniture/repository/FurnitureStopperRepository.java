package ro.sapientia.furniture.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.FurnitureStopper;

public interface FurnitureStopperRepository extends JpaRepository<FurnitureStopper, Long> {

	Optional<FurnitureStopper> findFurnitureStopperById(Long id);

	List<FurnitureStopper> findByFurnitureBodyId(Long furnitureBodyId);

}
