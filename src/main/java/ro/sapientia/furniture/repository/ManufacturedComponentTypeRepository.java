package ro.sapientia.furniture.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ro.sapientia.furniture.model.ManufacturedComponentType;

@Repository
public interface ManufacturedComponentTypeRepository extends JpaRepository<ManufacturedComponentType, Long> {
    Optional<ManufacturedComponentType> findByName(String name);
    boolean existsByName(String name);
}