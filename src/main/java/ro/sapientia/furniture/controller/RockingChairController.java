package ro.sapientia.furniture.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.RockingChairModel;
import ro.sapientia.furniture.service.RockingChairService;

import java.util.List;

@RestController
@RequestMapping("/rocking-chairs")
public class RockingChairController {
    private final RockingChairService rockingChairService;

    public RockingChairController(final RockingChairService rockingChairService) {
        this.rockingChairService = rockingChairService;
    }

    @GetMapping
    public ResponseEntity<List<RockingChairModel>> findAllRockingChairs() {
        return new ResponseEntity<>(rockingChairService.findAllRockingCharis(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<RockingChairModel>> findRockingChairById(final @PathVariable("id") Long id) {
        var result = rockingChairService.findRockingChairById(id);

        if (result == null || result.isEmpty()) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<RockingChairModel>>(result, HttpStatus.OK);
    }

    @GetMapping("/material/{material}")
    public ResponseEntity<List<RockingChairModel>> findRockingChairByMaterial(final @PathVariable("material") String material) {
        var result = rockingChairService.findRockingChairByMaterial(material);

        if (result == null || result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/rockingAngle")
    public ResponseEntity<List<RockingChairModel>> findRockingChairsByRockingAngle(
            @RequestParam("minAngle") double minAngle,
            @RequestParam("maxAngle") double maxAngle) {
        var result = rockingChairService.findRockingChairByRockingRadius(minAngle, maxAngle);

        if (result == null || result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RockingChairModel> createRockingChair(@RequestBody RockingChairModel rockingChair) {
        RockingChairModel createdRockingChair = rockingChairService.saveRockingChair(rockingChair);
        return new ResponseEntity<>(createdRockingChair, HttpStatus.CREATED);
    }


}
