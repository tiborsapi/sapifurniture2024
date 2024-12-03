package ro.sapientia.other.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.other.model.FurnitureOther;
import ro.sapientia.other.service.FurnitureOtherService;

@RestController
@RequestMapping("/furniture_other")
public class FurnitureOtherController {

	private final FurnitureOtherService furnitureOtherService;

	public FurnitureOtherController(FurnitureOtherService furnitureOtherService) {
		this.furnitureOtherService = furnitureOtherService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<FurnitureOther>> getAllFurnitureBodies() {
		final List<FurnitureOther> furnitureBodies = furnitureOtherService.findAllFurnitureOthers();
		return new ResponseEntity<>(furnitureBodies, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<FurnitureOther> getFurnitureBodyById(@PathVariable("id") Long id) {
		final FurnitureOther furnitureOther = furnitureOtherService.findFurnitureOtherById(id);
		return new ResponseEntity<>(furnitureOther, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<FurnitureOther> addFurnitureOther(@RequestBody FurnitureOther furnitureOther) {
		final FurnitureOther persistenFurnitureOther = furnitureOtherService.create(furnitureOther);
		return new ResponseEntity<>(persistenFurnitureOther, HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<FurnitureOther> updateFurnitureOther(@RequestBody FurnitureOther furnitureOther) {
		final FurnitureOther persistenFurnitureOther = furnitureOtherService.update(furnitureOther);
		return new ResponseEntity<>(persistenFurnitureOther, HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteFurnitureBodyById(@PathVariable("id") Long id) {
		furnitureOtherService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
