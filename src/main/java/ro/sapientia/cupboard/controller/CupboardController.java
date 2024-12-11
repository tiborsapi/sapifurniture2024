package ro.sapientia.cupboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.cupboard.model.Cupboard;
import ro.sapientia.cupboard.service.CupboardService;

import java.util.List;

@RestController
@RequestMapping("/cupboard")
public class CupboardController {

    private final CupboardService cupboardService;

    public CupboardController(final CupboardService cupboardService) {
        this.cupboardService = cupboardService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cupboard>> getAllCupboard(){
        final List<Cupboard> cupboards = cupboardService.findAllCupboard();
        return new ResponseEntity<>(cupboards, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Cupboard> getCupboardById(@PathVariable("id") Long id){
        final Cupboard cupboard = cupboardService.findCupboardById(id);
        return new ResponseEntity<>(cupboard,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cupboard> addCupboard(@RequestBody Cupboard cupboard){
        final Cupboard persistensCupboard = cupboardService.create(cupboard);
        return new ResponseEntity<>(persistensCupboard,HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Cupboard> updateCupboard(@RequestBody Cupboard cupboard){
        final Cupboard persistensCupboard = cupboardService.update(cupboard);
        return new ResponseEntity<>(persistensCupboard,HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteCupboardById(@PathVariable("id") Long id){
        cupboardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
