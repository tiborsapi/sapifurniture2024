package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

@Service
public class FurnitureBodyService {
	
	private final FurnitureBodyRepository furnitureBodyRepository;
	
	public FurnitureBodyService(final FurnitureBodyRepository furnitureBodyRepository) {
		this.furnitureBodyRepository = furnitureBodyRepository;
	}
	
	public List<FurnitureBodyDTO> findAllFurnitureBodies() {
		return this.furnitureBodyRepository.findAll();
	}

	public FurnitureBodyDTO findFurnitureBodyById(final Long id) {
		return this.furnitureBodyRepository.findFurnitureBodyById(id);
	}

	public FurnitureBodyDTO create(FurnitureBodyDTO furnitureBodyDTO) {
		return this.furnitureBodyRepository.saveAndFlush(furnitureBodyDTO);
	}

	public FurnitureBodyDTO update(FurnitureBodyDTO furnitureBodyDTO) {
		return this.furnitureBodyRepository.saveAndFlush(furnitureBodyDTO);
	}

	public void delete(Long id) {
		this.furnitureBodyRepository.deleteById(id);
	}

}
