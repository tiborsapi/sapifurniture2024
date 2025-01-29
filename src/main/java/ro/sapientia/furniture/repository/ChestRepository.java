package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.Chest;

public interface ChestRepository extends JpaRepository<Chest, Long> {

	Chest findChestById(Long id);

}
