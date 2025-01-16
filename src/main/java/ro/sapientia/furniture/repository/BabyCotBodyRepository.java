package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.BabyCotBody;

public interface BabyCotBodyRepository extends JpaRepository<BabyCotBody, Long> {

	BabyCotBody findBabyCotBodyById(Long id);

}
