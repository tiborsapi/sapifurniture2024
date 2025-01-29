package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.HallTreeBody;

public interface HallTreeBodyRepository  extends JpaRepository<HallTreeBody, Long> {
	HallTreeBody findHallTreeBodyById(Long id);
}
