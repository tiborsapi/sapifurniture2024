package ro.sapientia.furniture.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.furniture.model.Door;
import ro.sapientia.furniture.service.DoorService;

@RestController
@RequestMapping("/door")
public class DoorController{
	private final DoorService doorService;
	
	public DoorController(final DoorService doorService) {
		this.doorService= doorService;
		
	}
	@GetMapping("/all")
	public ResponseEntity<List<Door>> getAllDoors(){
		final List<Door> door = doorService.findAllDoors();
		return new ResponseEntity<>(door,HttpStatus.OK);
	}
	@GetMapping("/find/{id}")
	public ResponseEntity<Door> getDoorById(@PathVariable("id") Long id){
		final Door door = doorService.findDoorById(id);
		return new ResponseEntity<>(door,HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<Door> addDoor(@RequestBody Door door){
		final Door persistenDoor = doorService.create(door);
		return new ResponseEntity<>(persistenDoor,HttpStatus.CREATED);
	}
	@PostMapping("/update")
	public ResponseEntity<Door> updateDoor(@RequestBody Door door){
		final Door persistenDoor = doorService.update(door);
		return new ResponseEntity<>(persistenDoor,HttpStatus.OK);
	}
	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteDoorById(@PathVariable("id") Long id){
		doorService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}