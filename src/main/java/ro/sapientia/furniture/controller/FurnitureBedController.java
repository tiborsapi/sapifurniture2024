package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;
import ro.sapientia.furniture.service.FurnitureBedService;
import ro.sapientia.furniture.util.EnumConverter;

import java.util.List;

@RestController
@RequestMapping("/beds")
public class FurnitureBedController {
    private final FurnitureBedService furnitureBedService;

    public FurnitureBedController(final FurnitureBedService furnitureBedService) {
        this.furnitureBedService = furnitureBedService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FurnitureBed>> findAllBeds(){
        var result = furnitureBedService.findAllBeds();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureBed> findBedById(final @PathVariable("id") Long id){
        var result = furnitureBedService.findBedById(id);

        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/wood/{wood}")
    public ResponseEntity<List<FurnitureBed>> findBedsByWood(final @PathVariable("wood") String wood){

        WoodType woodType;
        try{
            woodType = EnumConverter.woodTypeEnumConverter(wood);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var result = furnitureBedService.findAllBedsByWood(woodType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<FurnitureBed>> findBedsByType(final @PathVariable("type") String type){

        BedType bedType;

        try {
            bedType = EnumConverter.bedTypeEnumConverter(type);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var result = furnitureBedService.findAllBedsByType(bedType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    public  ResponseEntity<FurnitureBed> createBed(@RequestBody final FurnitureBed furnitureBed){
        var result = this.furnitureBedService.saveBed(furnitureBed);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
