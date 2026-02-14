
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

import ro.sapientia.furniture.exception.CannotHangClothingException;
import ro.sapientia.furniture.exception.CannotTakeOffClothingException;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.HallTreeBody;
import ro.sapientia.furniture.service.HallTreeBodyService;

@RestController
@RequestMapping("/hallTree")
public class HallTreeController {
	
	private final HallTreeBodyService hallTreeBodyService;
	
	public HallTreeController(final HallTreeBodyService hallTreeBodyService)
	{
		this.hallTreeBodyService = hallTreeBodyService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<HallTreeBody>> getAllHallTreeBodies(){
		final List<HallTreeBody> hallTreeBodies = hallTreeBodyService.findAllHallTreeBodies();
		return new ResponseEntity<>(hallTreeBodies, HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<HallTreeBody> getHallTreeBodyById(@PathVariable("id") Long id){
		final HallTreeBody hallTreeBody = hallTreeBodyService.findHallTreeBodyById(id);
		return new ResponseEntity<>(hallTreeBody,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<HallTreeBody> addHallTreeBody(@RequestBody HallTreeBody hallTreeBody){
		final HallTreeBody persistenHallTreeBody = hallTreeBodyService.create(hallTreeBody);
		return new ResponseEntity<>(persistenHallTreeBody,HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<HallTreeBody> updateHallTreeBody(@RequestBody HallTreeBody hallTreeBody){
		final HallTreeBody persistenHallTreeBody = hallTreeBodyService.update(hallTreeBody);
		return new ResponseEntity<>(persistenHallTreeBody,HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteHallTreeBodyById(@PathVariable("id") Long id){
		hallTreeBodyService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/hangClothing/{id}")
	public ResponseEntity<?> hangClothing(@PathVariable("id") Long id) {
	    try {
	        hallTreeBodyService.hangClothing(hallTreeBodyService.findHallTreeBodyById(id));
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (CannotHangClothingException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}

	@PostMapping("/takeOffClothing/{id}")
	public ResponseEntity<?> takeOffClothing(@PathVariable("id") Long id) {
	    try {
	        hallTreeBodyService.takeOffClothing(hallTreeBodyService.findHallTreeBodyById(id));
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (CannotTakeOffClothingException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}

}
