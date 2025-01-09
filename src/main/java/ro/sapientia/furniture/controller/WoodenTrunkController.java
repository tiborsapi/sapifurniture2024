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

import ro.sapientia.furniture.model.WoodenTrunk;
import ro.sapientia.furniture.service.WoodenTrunkService;

@RestController
@RequestMapping("/wooden-trunk")
public class WoodenTrunkController {

    private final WoodenTrunkService woodenTrunkService;

    public WoodenTrunkController(final WoodenTrunkService woodenTrunkService) {
        this.woodenTrunkService = woodenTrunkService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<WoodenTrunk>> getAllWoodenTrunks() {
        final List<WoodenTrunk> woodenTrunks = woodenTrunkService.findAllWoodenTrunks();
        return new ResponseEntity<>(woodenTrunks, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<WoodenTrunk> getWoodenTrunkById(@PathVariable("id") Long id) {
        final WoodenTrunk woodenTrunk = woodenTrunkService.findWoodenTrunkById(id);
        return new ResponseEntity<>(woodenTrunk, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<WoodenTrunk> addWoodenTrunk(@RequestBody WoodenTrunk woodenTrunk) {
        final WoodenTrunk persistedWoodenTrunk = woodenTrunkService.create(woodenTrunk);
        return new ResponseEntity<>(persistedWoodenTrunk, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<WoodenTrunk> updateWoodenTrunk(@RequestBody WoodenTrunk woodenTrunk) {
        final WoodenTrunk persistedWoodenTrunk = woodenTrunkService.update(woodenTrunk);
        return new ResponseEntity<>(persistedWoodenTrunk, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteWoodenTrunkById(@PathVariable("id") Long id) {
        woodenTrunkService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
