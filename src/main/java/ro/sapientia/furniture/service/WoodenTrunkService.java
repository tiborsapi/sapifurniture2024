package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.WoodenTrunk;
import ro.sapientia.furniture.repository.WoodenTrunkRepository;

@Service
public class WoodenTrunkService {

    private final WoodenTrunkRepository woodenTrunkRepository;

    public WoodenTrunkService(final WoodenTrunkRepository woodenTrunkRepository) {
        this.woodenTrunkRepository = woodenTrunkRepository;
    }

    public List<WoodenTrunk> findAllWoodenTrunks() {
        return this.woodenTrunkRepository.findAll();
    }

    public WoodenTrunk findWoodenTrunkById(final Long id) {
        return this.woodenTrunkRepository.findWoodenTrunkById(id);
    }

    public WoodenTrunk create(WoodenTrunk woodenTrunk) {
        return this.woodenTrunkRepository.saveAndFlush(woodenTrunk);
    }

    public WoodenTrunk update(WoodenTrunk woodenTrunk) {
        return this.woodenTrunkRepository.saveAndFlush(woodenTrunk);
    }

    public void delete(Long id) {
        this.woodenTrunkRepository.deleteById(id);
    }
}