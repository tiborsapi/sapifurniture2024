package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia.furniture.model.WoodenTrunk;

public interface WoodenTrunkRepository extends JpaRepository<WoodenTrunk, Long> {

    WoodenTrunk findWoodenTrunkById(Long id);

}