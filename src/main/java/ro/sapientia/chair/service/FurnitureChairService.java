package ro.sapientia.chair.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.repository.FurnitureChairRepository;
import ro.sapientia.chair.repository.FurnitureChairRepository;
import ro.sapientia.furniture.model.FurnitureBody;

@Service
public class FurnitureChairService {

	private final FurnitureChairRepository furnitureChairRepository;

	public FurnitureChairService(FurnitureChairRepository FurnitureChairRepository) {
		this.furnitureChairRepository = FurnitureChairRepository;
	}

	public List<FurnitureChair> findAllFurnitureChairs() {
		return this.furnitureChairRepository.findAll();
	}

	public FurnitureChair findFurnitureChairById(final Long id) {
		return this.furnitureChairRepository.findFurnitureChairById(id);
	}

	public FurnitureChair create(FurnitureChair FurnitureChair) {
		return this.furnitureChairRepository.saveAndFlush(FurnitureChair);
	}

	public FurnitureChair update(FurnitureChair FurnitureChair) {
		return this.furnitureChairRepository.saveAndFlush(FurnitureChair);
	}

	public void delete(Long id) {
		this.furnitureChairRepository.deleteById(id);
	}

}
