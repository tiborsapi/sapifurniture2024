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

import ro.sapientia.furniture.model.BabyCotBody;
import ro.sapientia.furniture.service.BabyCotBodyService;

@RestController
@RequestMapping("/babycot")
public class BabyCotController {

	private final BabyCotBodyService babyCotBodyService;
	
	public BabyCotController(final BabyCotBodyService babyCotBodyService) {
		this.babyCotBodyService = babyCotBodyService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<BabyCotBody>> getAllBabyCotBodies(){
		final List<BabyCotBody> babyCotBodies = babyCotBodyService.findAllBabyCotBodies();
		return new ResponseEntity<>(babyCotBodies,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<BabyCotBody> getBabyCotBodyById(@PathVariable("id") Long id){
		final BabyCotBody babyCotBody = babyCotBodyService.findBabyCotBodyById(id);
		return new ResponseEntity<>(babyCotBody,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<BabyCotBody> addBabyCotBody(@RequestBody BabyCotBody babyCotBody){
		final BabyCotBody persistentBabyCotBody = babyCotBodyService.create(babyCotBody);
		return new ResponseEntity<>(persistentBabyCotBody,HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<BabyCotBody> updateBabyCotBody(@RequestBody BabyCotBody babyCotBody){
		final BabyCotBody persistentBabyCotBody = babyCotBodyService.update(babyCotBody);
		return new ResponseEntity<>(persistentBabyCotBody,HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteBabyCotBodyById(@PathVariable("id") Long id){
		babyCotBodyService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
