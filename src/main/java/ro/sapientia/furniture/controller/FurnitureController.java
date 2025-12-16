package ro.sapientia.furniture.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.service.FurnitureBodyService;

@RestController
@RequestMapping("/furniture")
public class FurnitureController {

	private final FurnitureBodyService furnitureBodyService;
	private final FurnitureMapper furnitureMapper;
	
	public FurnitureController(final FurnitureBodyService furnitureBodyService, final FurnitureMapper furnitureMapper) {
		this.furnitureBodyService = furnitureBodyService;
		this.furnitureMapper = furnitureMapper;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<FurnitureBodyDTO>> getAllFurnitureBodies(){
		final List<FurnitureBody> furnitureBodies = furnitureBodyService.findAllFurnitureBodies();
		final List<FurnitureBodyDTO> dtos = furnitureBodies.stream()
				.map(furnitureMapper::toDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<FurnitureBodyDTO> getFurnitureBodyById(@PathVariable("id") Long id){
		final FurnitureBody furnitureBody = furnitureBodyService.findFurnitureBodyById(id);
		final FurnitureBodyDTO dto = furnitureMapper.toDto(furnitureBody);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<FurnitureBodyDTO> addFurnitureBody(@RequestBody FurnitureBodyDTO furnitureBodyDTO){
		final FurnitureBody furnitureBody = furnitureMapper.toEntity(furnitureBodyDTO);
		final FurnitureBody persistentFurnitureBody = furnitureBodyService.create(furnitureBody);
		final FurnitureBodyDTO responseDto = furnitureMapper.toDto(persistentFurnitureBody);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<FurnitureBodyDTO> updateFurnitureBody(@RequestBody FurnitureBodyDTO furnitureBodyDTO){
		final FurnitureBody furnitureBody = furnitureMapper.toEntity(furnitureBodyDTO);
		final FurnitureBody persistentFurnitureBody = furnitureBodyService.update(furnitureBody);
		final FurnitureBodyDTO responseDto = furnitureMapper.toDto(persistentFurnitureBody);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteFurnitureBodyById(@PathVariable("id") Long id){
		furnitureBodyService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
