package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;
import ro.sapientia.furniture.repository.NightstandRepository;

import java.util.List;

@Service
public class NightstandService {

    private final NightstandRepository nightstandRepository;

    public NightstandService(final NightstandRepository nightstandRepository) {
        this.nightstandRepository = nightstandRepository;
    }

    public List<Nightstand> findAllNightstands() {
        return this.nightstandRepository.findAll();
    }

    public Nightstand findNightstandById(final Long id) {
        return this.nightstandRepository.findNightstandById(id);
    }

    public List<Nightstand> findNightstandsByColor(final NightstandColor color) {
        return this.nightstandRepository.findNightstandsByColor(color);
    }

    public Nightstand create(Nightstand nightstand) {
        return this.nightstandRepository.saveAndFlush(nightstand);
    }

    public void delete(Long id) {
        this.nightstandRepository.deleteById(id);
    }

}
