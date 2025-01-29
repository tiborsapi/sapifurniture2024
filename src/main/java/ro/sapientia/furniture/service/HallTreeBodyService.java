package ro.sapientia.furniture.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.exception.CannotHangClothingException;
import ro.sapientia.furniture.exception.CannotTakeOffClothingException;
import ro.sapientia.furniture.model.HallTreeBody;
import ro.sapientia.furniture.repository.HallTreeBodyRepository;

@Service
public class HallTreeBodyService {

	private final HallTreeBodyRepository hallTreeBodyRepository;
	
	public HallTreeBodyService(final HallTreeBodyRepository hallTreeBodyRepository) {
		this.hallTreeBodyRepository = hallTreeBodyRepository;
	}
	
	public List<HallTreeBody> findAllHallTreeBodies(){
		return this.hallTreeBodyRepository.findAll();
	}
	
	public HallTreeBody findHallTreeBodyById(final Long id) {
		return this.hallTreeBodyRepository.findHallTreeBodyById(id);
	}
	
	public HallTreeBody create(HallTreeBody hallTreeBody) {
		return this.hallTreeBodyRepository.saveAndFlush(hallTreeBody);
	}
	
	public HallTreeBody update(HallTreeBody hallTreeBody) {
		return this.hallTreeBodyRepository.saveAndFlush(hallTreeBody);
	}
	
	public void delete(Long id) {
		this.hallTreeBodyRepository.deleteById(id);
	}
	
	public void hangClothing(HallTreeBody hallTreeBody) {
	    if (hallTreeBody.getHangers() < hallTreeBody.getHangedClothes() + 1) {
	        throw new CannotHangClothingException("Cannot hang clothing: no more space.");
	    }
	    hallTreeBody.setHangedClothes(hallTreeBody.getHangedClothes() + 1);
	}

	public void takeOffClothing(HallTreeBody hallTreeBody) {
	    if (hallTreeBody.getHangedClothes() - 1 < 0) {
	        throw new CannotTakeOffClothingException("Cannot take off clothing: no clothes are hung.");
	    }
	    hallTreeBody.setHangedClothes(hallTreeBody.getHangedClothes() - 1);
	}
}
