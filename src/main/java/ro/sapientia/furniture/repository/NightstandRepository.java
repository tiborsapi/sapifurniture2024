package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;

import java.util.List;

public interface NightstandRepository extends JpaRepository<Nightstand, Long> {

    Nightstand findNightstandById(Long id);

    List<Nightstand> findNightstandsByColor(NightstandColor color);

}
