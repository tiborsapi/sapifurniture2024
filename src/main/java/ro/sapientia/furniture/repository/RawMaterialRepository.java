package ro.sapientia.furniture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    Optional<RawMaterial> findByRawMaterialTypeAndLengthAndWidthAndHeight(RawMaterialType rawMaterialType, Integer length, Integer width, Integer height);
}
