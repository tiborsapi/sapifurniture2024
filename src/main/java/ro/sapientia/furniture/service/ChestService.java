package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.Chest;
import ro.sapientia.furniture.repository.ChestRepository;

@Service
public class ChestService {
	
	private final ChestRepository chestRepository;
	
	public ChestService(final ChestRepository furnitureBodyRepository) {
		this.chestRepository = furnitureBodyRepository;
	}
	
	public List<Chest> findAllChests() {
		return this.chestRepository.findAll();
	}

	public Chest findChestById(final Long id) {
		return this.chestRepository.findChestById(id);
	}

	public Chest create(Chest chest) {
		return this.chestRepository.saveAndFlush(chest);
	}

	public Chest update(Chest chest) {
		return this.chestRepository.saveAndFlush(chest);
	}

	public void delete(Long id) {
		this.chestRepository.deleteById(id);
	}

}
