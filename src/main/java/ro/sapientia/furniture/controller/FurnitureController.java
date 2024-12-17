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

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.service.FurnitureBodyService;

@RestController
@RequestMapping("/furniture")
public class FurnitureController {

	private final FurnitureBodyService furnitureBodyService;
	
	public FurnitureController(final FurnitureBodyService furnitureBodyService) {
		this.furnitureBodyService = furnitureBodyService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<FurnitureBody>> getAllFurnitureBodies(){
		final List<FurnitureBody> furnitureBodies = furnitureBodyService.findAllFurnitureBodies();
		return new ResponseEntity<>(furnitureBodies,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<FurnitureBody> getFurnitureBodyById(@PathVariable("id") Long id){
		final FurnitureBody furnitureBody = furnitureBodyService.findFurnitureBodyById(id);
		return new ResponseEntity<>(furnitureBody,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<FurnitureBody> addFurnitureBody(@RequestBody FurnitureBody furnitureBody){
		final FurnitureBody persistenFurnitureBody = furnitureBodyService.create(furnitureBody);
		return new ResponseEntity<>(persistenFurnitureBody,HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<FurnitureBody> updateFurnitureBody(@RequestBody FurnitureBody furnitureBody){
		final FurnitureBody persistenFurnitureBody = furnitureBodyService.update(furnitureBody);
		return new ResponseEntity<>(persistenFurnitureBody,HttpStatus.OK);
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteFurnitureBodyById(@PathVariable("id") Long id){
		furnitureBodyService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
