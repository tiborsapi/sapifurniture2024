package ro.sapientia.furniture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ro.sapientia.furniture.model.ManufacturedComponent;

@Repository
public interface ManufacturedComponentRepository extends JpaRepository<ManufacturedComponent, Long> {
    List<ManufacturedComponent> findByComponentListId(Long componentListId);
    List<ManufacturedComponent> findByManufacturedComponentTypeId(Long manufacturedComponentTypeId);
}