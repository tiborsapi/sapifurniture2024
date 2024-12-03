package ro.sapientia.other.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.other.model.FurnitureOther;

public interface FurnitureOtherRepository extends JpaRepository<FurnitureOther, Long> {

	FurnitureOther findFurnitureOtherById(Long id);
}
