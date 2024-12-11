package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.Cupboard;

public interface CupboardRepository extends JpaRepository<Cupboard, Long> {

    Cupboard findCupboardById(Long id);

}
