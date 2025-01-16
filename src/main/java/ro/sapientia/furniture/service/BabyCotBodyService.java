package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.BabyCotBody;
import ro.sapientia.furniture.repository.BabyCotBodyRepository;

@Service
public class BabyCotBodyService {
	
	private final BabyCotBodyRepository babyCotBodyRepository;
	
	public BabyCotBodyService(final BabyCotBodyRepository babyCotBodyRepository) {
		this.babyCotBodyRepository = babyCotBodyRepository;
	}
	
	public List<BabyCotBody> findAllBabyCotBodies() {
		return this.babyCotBodyRepository.findAll();
	}

	public BabyCotBody findBabyCotBodyById(final Long id) {
		return this.babyCotBodyRepository.findBabyCotBodyById(id);
	}

	public BabyCotBody create(BabyCotBody babyCotBody) {
		return this.babyCotBodyRepository.saveAndFlush(babyCotBody);
	}

	public BabyCotBody update(BabyCotBody babyCotBody) {
		return this.babyCotBodyRepository.saveAndFlush(babyCotBody);
	}

	public void delete(Long id) {
		this.babyCotBodyRepository.deleteById(id);
	}

}
