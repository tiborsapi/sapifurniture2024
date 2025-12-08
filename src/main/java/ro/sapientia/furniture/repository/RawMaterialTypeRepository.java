package ro.sapientia.furniture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.RawMaterialType;

public interface RawMaterialTypeRepository extends JpaRepository<RawMaterialType, Long> {
    Optional<RawMaterialType> findByName(String name);
}
