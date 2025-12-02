package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;

public interface FurnitureBodyRepository extends JpaRepository<FurnitureBodyDTO, Long> {

	FurnitureBodyDTO findFurnitureBodyById(Long id);

}
