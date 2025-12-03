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

import ro.sapientia.furniture.model.dto.CutRequestDTO;
import ro.sapientia.furniture.model.dto.CutResponseDTO;
import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.service.CutOptimizationService;
import ro.sapientia.furniture.service.FurnitureBodyService;

@RestController
@RequestMapping("/furniture")
public class FurnitureController {

	private final FurnitureBodyService furnitureBodyService;
	private final CutOptimizationService cutOptimizationService;

	public FurnitureController(final FurnitureBodyService furnitureBodyService,
							   final CutOptimizationService cutOptimizationService) {
		this.furnitureBodyService = furnitureBodyService;
		this.cutOptimizationService = cutOptimizationService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<FurnitureBodyDTO>> getAllFurnitureBodies(){
		final List<FurnitureBodyDTO> furnitureBodies = furnitureBodyService.findAllFurnitureBodies();
		return new ResponseEntity<>(furnitureBodies,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<FurnitureBodyDTO> getFurnitureBodyById(@PathVariable("id") Long id){
		final FurnitureBodyDTO furnitureBodyDTO = furnitureBodyService.findFurnitureBodyById(id);
		return new ResponseEntity<>(furnitureBodyDTO,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<FurnitureBodyDTO> addFurnitureBody(@RequestBody FurnitureBodyDTO furnitureBodyDTO){
		final FurnitureBodyDTO persistenFurnitureBodyDTO = furnitureBodyService.create(furnitureBodyDTO);
		return new ResponseEntity<>(persistenFurnitureBodyDTO,HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<FurnitureBodyDTO> updateFurnitureBody(@RequestBody FurnitureBodyDTO furnitureBodyDTO){
		final FurnitureBodyDTO persistenFurnitureBodyDTO = furnitureBodyService.update(furnitureBodyDTO);
		return new ResponseEntity<>(persistenFurnitureBodyDTO,HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteFurnitureBodyById(@PathVariable("id") Long id){
		furnitureBodyService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/cut")
	public ResponseEntity<CutResponseDTO> optimizeCut(@RequestBody CutRequestDTO cutRequestDTO){
		final CutResponseDTO cutResponseDTO = cutOptimizationService.optimizeCutting(cutRequestDTO);
		return new ResponseEntity<>(cutResponseDTO, HttpStatus.OK);
	}
}
