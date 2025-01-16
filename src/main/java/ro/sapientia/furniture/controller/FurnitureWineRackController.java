package ro.sapientia.furniture.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.FurnitureWineRack;
import ro.sapientia.furniture.service.FurnitureWineRackService;

import java.util.List;

@RestController
@RequestMapping("/winerack")
public class FurnitureWineRackController {

    private final FurnitureWineRackService furnitureWineRackService;

    public FurnitureWineRackController(final FurnitureWineRackService furnitureWineRackService) {
        this.furnitureWineRackService = furnitureWineRackService;
    }

    @GetMapping
    public ResponseEntity<List<FurnitureWineRack>> findAllWineRack() {
        var resultData = furnitureWineRackService.findAllFurnitureWineRack();
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureWineRack> findTableById(final @PathVariable("id") Long id) {
        var resultData = furnitureWineRackService.findFurnitureWineRackById(id);

        if (resultData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultData, HttpStatus.OK);

    }

    @GetMapping("/capacity/{minCapacity}/{maxCapacity}")
    public ResponseEntity<List<FurnitureWineRack>> findAllFurnitureWineRackBetweenTwoCapacity(final @PathVariable("minCapacity") int minCapacity, @PathVariable("maxCapacity") int maxCapacity) {

        if (minCapacity > maxCapacity) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var resultData = furnitureWineRackService.findAllFurnitureWineRacksByCapacityBetween(minCapacity, maxCapacity);
        if (resultData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/currentLoad/{minLoad}/{maxLoad}")
    public ResponseEntity<List<FurnitureWineRack>> findAllFurnitureWineRackBetweenTwoCurrentLoadNumber(final @PathVariable("minLoad") int minLoad, @PathVariable("maxLoad") int maxLoad) {

        if (minLoad > maxLoad) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var resultData = furnitureWineRackService.findAllFurnitureWineRacksByCurrentLoadNumberBetween(minLoad, maxLoad);
        if (resultData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/transparentFront/{transparentFrontView}")
    public ResponseEntity<List<FurnitureWineRack>> findAllFurnitureWineRackByFrontView(final @PathVariable("transparentFrontView") boolean transparentFrontView) {

        var resultData = furnitureWineRackService.findAllFurnitureWineRacksByFrontView(transparentFrontView);
        if (resultData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FurnitureWineRack> createWineRack(@RequestBody final FurnitureWineRack furnitureWineRack) {

        try {
            FurnitureWineRack savedWineRack = furnitureWineRackService.saveFurnitureWineRack(furnitureWineRack);
            return new ResponseEntity<>(savedWineRack, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
