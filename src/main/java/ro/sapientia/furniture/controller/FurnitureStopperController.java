package ro.sapientia.furniture.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.furniture.dto.StopFurnitureRequestDTO;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FurnitureStopper;
import ro.sapientia.furniture.service.FurnitureStopperService;

@RestController
@RequestMapping("/furniture-stopper")
public class FurnitureStopperController {

	private final FurnitureStopperService furnitureStopperService;

	public FurnitureStopperController(final FurnitureStopperService furnitureStopperService) {
		this.furnitureStopperService = furnitureStopperService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<FurnitureStopper>> getAllFurnitureBodies() {
		final List<FurnitureStopper> furnitureBodies = furnitureStopperService.findAllFurnitureStoppers();
		return new ResponseEntity<>(furnitureBodies, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<FurnitureStopper> getFurnitureStopperById(@PathVariable("id") Long id) {
		final Optional<FurnitureStopper> furnitureStopper = furnitureStopperService.findFurnitureStopperById(id);

		if (!furnitureStopper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(furnitureStopper.get(), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<FurnitureStopper> addFurnitureStopper(@RequestBody FurnitureStopper furnitureStopper) {
		final FurnitureStopper persistenFurnitureStopper = furnitureStopperService.create(furnitureStopper);
		return new ResponseEntity<>(persistenFurnitureStopper, HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<FurnitureStopper> updateFurnitureStopper(@RequestBody FurnitureStopper furnitureStopper) {
		final FurnitureStopper persistenFurnitureStopper = furnitureStopperService.update(furnitureStopper);
		return new ResponseEntity<>(persistenFurnitureStopper, HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteFurnitureStopperById(@PathVariable("id") Long id) {
		furnitureStopperService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/stop")
	public ResponseEntity<FurnitureStopper> stopFurniture(
			@RequestBody StopFurnitureRequestDTO stopFurnitureRequestDTO) {
		FurnitureBody furnitureBody = new FurnitureBody();
		furnitureBody.setId(stopFurnitureRequestDTO.getFurnitureBodyId());

		final Optional<FurnitureStopper> furnitureStopper = furnitureStopperService.stopFurniture(
				stopFurnitureRequestDTO.getFurnitureStopperId(), furnitureBody);

		if (furnitureStopper.isPresent()) {
			return new ResponseEntity<>(furnitureStopper.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
