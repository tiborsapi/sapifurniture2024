package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.Wardrobe;
import ro.sapientia.furniture.repository.WardrobeRepository;

@Service
public class WardrobeService {
	
	private final WardrobeRepository wardrobeRepository;
	
	public WardrobeService(final WardrobeRepository wardrobeRepository) {
		this.wardrobeRepository = wardrobeRepository;
	}
	
	public List<Wardrobe> findAllWardrobe() {
		return this.wardrobeRepository.findAll();
	}

	public Wardrobe findWardrobeById(final Long id) {
		return this.wardrobeRepository.findWardrobeById(id);
	}

	public Wardrobe create(Wardrobe wardrobe) {
		return this.wardrobeRepository.saveAndFlush(wardrobe);
	}

	public Wardrobe update(Wardrobe wardrobe) {
		return this.wardrobeRepository.saveAndFlush(wardrobe);
	}

	public void delete(Long id) {
		this.wardrobeRepository.deleteById(id);
	}

}
