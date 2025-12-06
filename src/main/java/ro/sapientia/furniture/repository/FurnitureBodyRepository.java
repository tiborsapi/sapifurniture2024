package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.entities.FurnitureBody;

public interface FurnitureBodyRepository extends JpaRepository<FurnitureBody, Long> {

	FurnitureBody findFurnitureBodyById(Long id);

}
