package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.sapientia.furniture.model.Drawer;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrawerRepository extends JpaRepository<Drawer, Long> {

    List<Drawer> findByMaterial(String material);

    List<Drawer> findByIsOpenTrue();


    @Query("SELECT d FROM Drawer d WHERE d.weightCapacity >= :minCapacity")
    List<Drawer> findByMinimumWeightCapacity(@Param("minCapacity") Double minCapacity);

    @Query("SELECT d FROM Drawer d WHERE d.width = :width AND d.height = :height AND d.depth = :depth")
    List<Drawer> findByDimensions(@Param("width") Double width,
                                  @Param("height") Double height,
                                  @Param("depth") Double depth);

    @Modifying
    @Query("UPDATE Drawer d SET d.isOpen = :isOpen WHERE d.id = :id")
    int updateDrawerState(@Param("id") Long id, @Param("isOpen") boolean isOpen);

    @Query(value = "SELECT * FROM drawer WHERE weight_capacity > :capacity ORDER BY weight_capacity DESC LIMIT 5",
            nativeQuery = true)
    List<Drawer> findTopDrawersByWeightCapacity(@Param("capacity") Double capacity);

    Optional<Drawer> findFirstByOrderByWeightCapacityDesc();

    boolean existsByMaterialAndColor(String material, String color);
}