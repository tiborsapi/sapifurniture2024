package ro.sapientia.chair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.chair.model.FurnitureChair;


public interface FurnitureChairRepository extends JpaRepository<FurnitureChair, Long> {

	FurnitureChair findFurnitureChairById(Long id);
}
