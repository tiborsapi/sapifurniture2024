package ro.sapientia.cupboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.cupboard.model.Cupboard;

public interface CupboardRepository extends JpaRepository<Cupboard, Long> {

    Cupboard findCupboardById(Long id);

}
