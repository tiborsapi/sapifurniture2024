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

import ro.sapientia.furniture.model.Wardrobe;
import ro.sapientia.furniture.service.WardrobeService;

@RestController
@RequestMapping("/wardrobe")
public class WardrobeController {

	private final WardrobeService wardrobeService;

	public WardrobeController(final WardrobeService wardrobeService) {
		this.wardrobeService = wardrobeService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Wardrobe>> getAllFurnitureBodies() {
		final List<Wardrobe> furnitureBodies = wardrobeService.findAllWardrobe();
		return new ResponseEntity<>(furnitureBodies, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Wardrobe> getFurnitureBodyById(@PathVariable("id") Long id) {
		final Wardrobe wardrobe = wardrobeService.findWardrobeById(id);
		return new ResponseEntity<>(wardrobe, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Wardrobe> addFurnitureBody(@RequestBody Wardrobe wardrobe) {
		final Wardrobe persistedWardrobe = wardrobeService.create(wardrobe);
		return new ResponseEntity<>(persistedWardrobe, HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<Wardrobe> updateFurnitureBody(@RequestBody Wardrobe wardrobe) {
		final Wardrobe persistenWardrobe = wardrobeService.update(wardrobe);
		return new ResponseEntity<>(persistenWardrobe, HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteFurnitureBodyById(@PathVariable("id") Long id) {
		wardrobeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
