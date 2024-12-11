package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.Tvstand;

public interface TvStandRepository extends JpaRepository<Tvstand, Long> {

	Tvstand findTvStandById(Long id);

}
