package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

@Service
public class FurnitureBodyService {

	private final FurnitureBodyRepository furnitureBodyRepository;

	public FurnitureBodyService(final FurnitureBodyRepository furnitureBodyRepository) {
		this.furnitureBodyRepository = furnitureBodyRepository;
	}

	public List<FurnitureBody> findAllFurnitureBodies() {
		return this.furnitureBodyRepository.findAll();
	}

	public FurnitureBody findFurnitureBodyById(final Long id) {
		return this.furnitureBodyRepository.findFurnitureBodyById(id);
	}

	public FurnitureBody create(FurnitureBody furnitureBody) {
		return this.furnitureBodyRepository.saveAndFlush(furnitureBody);
	}

	public FurnitureBody update(FurnitureBody furnitureBody) {
		return this.furnitureBodyRepository.saveAndFlush(furnitureBody);
	}

	public void delete(Long id) {
		this.furnitureBodyRepository.deleteById(id);
	}

}