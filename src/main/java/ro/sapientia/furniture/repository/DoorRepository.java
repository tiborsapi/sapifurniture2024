package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.Door;

public interface DoorRepository extends JpaRepository<Door, Long> {
	Door findDoorById(Long id);
	
}

