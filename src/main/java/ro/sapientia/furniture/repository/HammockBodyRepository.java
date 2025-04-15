package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.HammockBody;

public interface HammockBodyRepository extends JpaRepository<HammockBody, Long> {
    HammockBody findHammockBodyById(Long id);
    HammockBody findHammockBodyByMaterial(String material);
    HammockBody findHammockBodyByWeight(Double weight);
    HammockBody findHammockBodyByLengthAndWidth(Double length, Double width);
}
