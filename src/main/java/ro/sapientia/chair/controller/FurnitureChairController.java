package ro.sapientia.chair.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.service.FurnitureChairService;

@RestController
@RequestMapping("/furniture_chair")
public class FurnitureChairController {

	private final FurnitureChairService FurnitureChairService;

	public FurnitureChairController(FurnitureChairService FurnitureChairService) {
		this.FurnitureChairService = FurnitureChairService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<FurnitureChair>> getAllFurnitureChairs() {
		final List<FurnitureChair> FurnitureChairs = FurnitureChairService.findAllFurnitureChairs();
		return new ResponseEntity<>(FurnitureChairs, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<FurnitureChair> getFurnitureChairById(@PathVariable("id") Long id) {
		final FurnitureChair FurnitureChair = FurnitureChairService.findFurnitureChairById(id);
		return new ResponseEntity<>(FurnitureChair, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<FurnitureChair> addFurnitureChair(@RequestBody FurnitureChair FurnitureChair) {
		final FurnitureChair persistenFurnitureChair = FurnitureChairService.create(FurnitureChair);
		return new ResponseEntity<>(persistenFurnitureChair, HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<FurnitureChair> updateFurnitureChair(@RequestBody FurnitureChair FurnitureChair) {
		final FurnitureChair persistenFurnitureChair = FurnitureChairService.update(FurnitureChair);
		return new ResponseEntity<>(persistenFurnitureChair, HttpStatus.OK);
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteFurnitureChairById(@PathVariable("id") Long id) {
		FurnitureChairService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
