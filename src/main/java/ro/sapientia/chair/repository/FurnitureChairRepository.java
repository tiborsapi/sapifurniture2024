package ro.sapientia.chair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.chair.model.FurnitureChair;
import org.springframework.stereotype.Repository;


public interface FurnitureChairRepository extends JpaRepository<FurnitureChair, Long> {
	FurnitureChair findFurnitureChairById(Long id);
}
