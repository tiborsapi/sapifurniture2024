package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Wardrobe;

public interface WardrobeRepository extends JpaRepository<Wardrobe, Long> {

	Wardrobe findWardrobeById(Long id);

}
