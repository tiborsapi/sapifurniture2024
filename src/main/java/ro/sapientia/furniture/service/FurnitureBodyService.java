package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.mapper.FurnitureBodyMapper;
import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.model.entities.FurnitureBody;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

@Service
public class FurnitureBodyService {
	
	private final FurnitureBodyRepository furnitureBodyRepository;
	
	public FurnitureBodyService(final FurnitureBodyRepository furnitureBodyRepository) {
		this.furnitureBodyRepository = furnitureBodyRepository;
	}
	
	public List<FurnitureBodyDTO> findAllFurnitureBodies() {
		List<FurnitureBody> entities = this.furnitureBodyRepository.findAll();
		return FurnitureBodyMapper.toDTOList(entities);
	}

	public FurnitureBodyDTO findFurnitureBodyById(final Long id) {
		FurnitureBody entity = this.furnitureBodyRepository.findFurnitureBodyById(id);
		return FurnitureBodyMapper.toDTO(entity);
	}

	public FurnitureBodyDTO create(FurnitureBodyDTO furnitureBodyDTO) {
		FurnitureBody entity = FurnitureBodyMapper.toEntity(furnitureBodyDTO);
		FurnitureBody savedEntity = this.furnitureBodyRepository.saveAndFlush(entity);
		return FurnitureBodyMapper.toDTO(savedEntity);
	}

	public FurnitureBodyDTO update(FurnitureBodyDTO furnitureBodyDTO) {
		FurnitureBody entity = FurnitureBodyMapper.toEntity(furnitureBodyDTO);
		FurnitureBody updatedEntity = this.furnitureBodyRepository.saveAndFlush(entity);
		return FurnitureBodyMapper.toDTO(updatedEntity);
	}

	public void delete(Long id) {
		this.furnitureBodyRepository.deleteById(id);
	}

}
