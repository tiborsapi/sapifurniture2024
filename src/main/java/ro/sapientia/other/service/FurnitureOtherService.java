package ro.sapientia.other.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.other.model.FurnitureOther;
import ro.sapientia.other.repository.FurnitureOtherRepository;

@Service
public class FurnitureOtherService {

	private final FurnitureOtherRepository furnitureOtherRepository;

	public FurnitureOtherService(FurnitureOtherRepository furnitureOtherRepository) {
		this.furnitureOtherRepository = furnitureOtherRepository;
	}

	public List<FurnitureOther> findAllFurnitureBodies() {
		return this.furnitureOtherRepository.findAll();
	}

	public FurnitureOther findFurnitureBodyById(final Long id) {
		return this.furnitureOtherRepository.findFurnitureOther(id);
	}

	public FurnitureOther create(FurnitureOther furnitureOther) {
		return this.furnitureOtherRepository.saveAndFlush(furnitureOther);
	}

	public FurnitureOther update(FurnitureOther FurnitureOther) {
		return this.furnitureOtherRepository.saveAndFlush(FurnitureOther);
	}

	public void delete(Long id) {
		this.furnitureOtherRepository.deleteById(id);
	}

}
