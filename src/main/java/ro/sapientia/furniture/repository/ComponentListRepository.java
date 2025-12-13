package ro.sapientia.furniture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.ComponentList;

public interface ComponentListRepository extends JpaRepository<ComponentList, Long> {
    List<ComponentList> findByFurnitureBody_Id(Long furnitureBodyId);
}
