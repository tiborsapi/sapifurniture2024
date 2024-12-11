package tvstand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.FurnitureBody;
import tvstand.model.Tvstand;

public interface TvStandRepository extends JpaRepository<Tvstand, Long> {

	Tvstand findTvStandById(Long id);

}
