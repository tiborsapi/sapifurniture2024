package ro.sapientia.furniture.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.FurnitureStopper;
import ro.sapientia.furniture.repository.FurnitureStopperRepository;

@Service
public class FurnitureStopperService {

	private final FurnitureStopperRepository furnitureStopperRepository;

	public FurnitureStopperService(final FurnitureStopperRepository furnitureStopperRepository) {
		this.furnitureStopperRepository = furnitureStopperRepository;
	}

	public List<FurnitureStopper> findAllFurnitureStoppers() {
		return this.furnitureStopperRepository.findAll();
	}

	public Optional<FurnitureStopper> findFurnitureStopperById(final Long id) {
		return this.furnitureStopperRepository.findFurnitureStopperById(id);
	}

	public FurnitureStopper create(FurnitureStopper furnitureStopper) {
		return this.furnitureStopperRepository.saveAndFlush(furnitureStopper);
	}

	public FurnitureStopper update(FurnitureStopper furnitureStopper) {
		return this.furnitureStopperRepository.saveAndFlush(furnitureStopper);
	}

	public void delete(Long id) {
		this.furnitureStopperRepository.deleteById(id);
	}

}
