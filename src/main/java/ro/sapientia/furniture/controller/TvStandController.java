package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.service.TvStandService;

import java.util.List;

@RestController
@RequestMapping("/tvstand")
public class TvStandController {

	private final TvStandService tvStandService;

	public TvStandController(final TvStandService tvStandService) {
		this.tvStandService = tvStandService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Tvstand>> getAllTvStands(){
		final List<Tvstand> tvStands = tvStandService.findAllTvStand();
		return new ResponseEntity<>(tvStands,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Tvstand> getTvStandById(@PathVariable("id") Long id){
		final Tvstand tvStand = tvStandService.findTvStandById(id);
		return new ResponseEntity<>(tvStand,HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Tvstand> addTvStand(@RequestBody Tvstand tvStand){
		final Tvstand persistenTvStand = tvStandService.create(tvStand);
		return new ResponseEntity<>(persistenTvStand,HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<Tvstand> updateTvStand(@RequestBody Tvstand tvStand){
		final Tvstand persistenTvStand = tvStandService.update(tvStand);
		return new ResponseEntity<>(persistenTvStand,HttpStatus.OK);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<?> deleteTvStandById(@PathVariable("id") Long id){
		tvStandService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
