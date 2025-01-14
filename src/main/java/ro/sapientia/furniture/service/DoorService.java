package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.Door;
import ro.sapientia.furniture.repository.DoorRepository;

@Service
public class DoorService{
	private final DoorRepository doorRepository;
	
	public DoorService(final DoorRepository doorRepository) {
		this.doorRepository = doorRepository;
		
	}
	public List<Door> findAllDoors() {
		return this.doorRepository.findAll();
	}
	public Door findDoorById(final Long id) {
		return this.doorRepository.findDoorById(id);
	}
	public Door create(Door door) {
		return this.doorRepository.saveAndFlush(door);
	}
	public Door update(Door door) {
		return this.doorRepository.saveAndFlush(door);
	}
	public void delete(Long id) {
		this.doorRepository.deleteById(id);
	}
	
	


}
