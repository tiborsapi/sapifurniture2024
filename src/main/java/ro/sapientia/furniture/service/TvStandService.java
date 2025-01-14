package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.repository.TvStandRepository;

import java.util.List;

@Service
public class TvStandService {

	private final TvStandRepository tvStandRepository;

	public TvStandService(final TvStandRepository tvStandRepository) {
		this.tvStandRepository = tvStandRepository;
	}

	public List<Tvstand> findAllTvStand() {
		return this.tvStandRepository.findAll();
	}

	public Tvstand findTvstandById(final Long id) {
		return this.tvStandRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tvstand not found with id: " + id));
	}

	public Tvstand create(Tvstand tvStand) {
		return this.tvStandRepository.saveAndFlush(tvStand);
	}

	public Tvstand update(Tvstand tvStand) {
		return this.tvStandRepository.saveAndFlush(tvStand);
	}

	public void delete(Long id) {
		Tvstand tvstand = findTvstandById(id);  // Először ellenőrizzük, hogy létezik-e
		this.tvStandRepository.deleteById(id);
	}
}
