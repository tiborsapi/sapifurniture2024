package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.HammockBody;
import ro.sapientia.furniture.service.HammockBodyService;

import java.util.List;

@RestController
@RequestMapping("/hammock")
public class HammockController {
    private final HammockBodyService hammockBodyService;

    public HammockController(final HammockBodyService hammockBodyService) {
        this.hammockBodyService = hammockBodyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HammockBody>> getAllHammockBodies() {
        final List<HammockBody> hammockBodies = hammockBodyService.findAllHammockBodies();
        return new ResponseEntity<>(hammockBodies, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<HammockBody> getHammockBodyById(@PathVariable("id") Long id) {
        final HammockBody hammockBody = hammockBodyService.findHammockBodyById(id);
        return new ResponseEntity<>(hammockBody, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HammockBody> addHammockBody(@RequestBody HammockBody hammockBody) {
        final HammockBody persistenHammockBody = hammockBodyService.create(hammockBody);
        return new ResponseEntity<>(persistenHammockBody, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<HammockBody> updateHammockBody(@RequestBody HammockBody hammockBody) {
        final HammockBody persistenHammockBody = hammockBodyService.update(hammockBody);
        return new ResponseEntity<>(persistenHammockBody, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteHammockBodyById(@PathVariable("id") Long id) {
        hammockBodyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
