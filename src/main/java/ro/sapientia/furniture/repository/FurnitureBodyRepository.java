package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.FurnitureBody;


public interface FurnitureBodyRepository extends JpaRepository<FurnitureBody, Long> {
	FurnitureBody findFurnitureBodyById(Long id);

}
