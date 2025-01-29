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

import ro.sapientia.furniture.model.Chest;
import ro.sapientia.furniture.service.ChestService;

@RestController
@RequestMapping("/chest")
public class ChestController {

	private final ChestService chestService;
	
	public ChestController(final ChestService chestService) {
		this.chestService = chestService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Chest>> getAllFurnitureBodies(){
		final List<Chest> furnitureBodies = chestService.findAllChests();
		return new ResponseEntity<>(furnitureBodies,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Chest> getChestById(@PathVariable("id") Long id){
		final Chest chest= chestService.findChestById(id);
		return new ResponseEntity<>(chest,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Chest> addChest(@RequestBody Chest chest){
		final Chest persistenChest= chestService.create(chest);
		return new ResponseEntity<>(persistenChest,HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<Chest> updateChest(@RequestBody Chest chest){
		final Chest persistenChest = chestService.update(chest);
		return new ResponseEntity<>(persistenChest,HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteChestById(@PathVariable("id") Long id){
		chestService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
