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

import ro.sapientia.furniture.model.FurnitureSwivelChair;
import ro.sapientia.furniture.service.FurnitureSwivelChairService;

@RestController
@RequestMapping("/swivelchair")
public class FurnitureSwivelChairController {

    private final FurnitureSwivelChairService furnitureSwivelChairService;

    public FurnitureSwivelChairController(final FurnitureSwivelChairService furnitureSwivelChairService) {
        this.furnitureSwivelChairService = furnitureSwivelChairService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FurnitureSwivelChair>> findAllSwivelChairs() {
        final List<FurnitureSwivelChair> furnitureSwivelChairs = furnitureSwivelChairService.findAllFurnitureSwivelChairs();
        return new ResponseEntity<>(furnitureSwivelChairs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureSwivelChair> findSwivelChairById(final @PathVariable("id") Long id) {
        final FurnitureSwivelChair furnitureSwivelChair = furnitureSwivelChairService.findFurnitureSwivelChairById(id);
        return new ResponseEntity<>(furnitureSwivelChair, HttpStatus.OK);
    }

    @GetMapping("/weightcapacity/{weightcapacity}")
    public ResponseEntity<List<FurnitureSwivelChair>> findAllSwivelChairsByWeightCapacity(final @PathVariable("weightcapacity") int weightCapacity) {
        final List<FurnitureSwivelChair> furnitureSwivelChairs = furnitureSwivelChairService.findAllFurnitureSwivelChairByWeightCapacity(weightCapacity);
        return new ResponseEntity<>(furnitureSwivelChairs, HttpStatus.OK);
    }

    @GetMapping("/material/{material}")
    public ResponseEntity<List<FurnitureSwivelChair>> findAllSwivelChairsByMaterial(final @PathVariable("material") String material) {
        final List<FurnitureSwivelChair> furnitureSwivelChairs = furnitureSwivelChairService.findAllFurnitureSwivelChairByMaterial(material);
        return new ResponseEntity<>(furnitureSwivelChairs, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FurnitureSwivelChair> addSwivelChair(@RequestBody FurnitureSwivelChair furnitureSwivelChair) {
        final FurnitureSwivelChair persistentFurnitureSwivelChair = furnitureSwivelChairService.create(furnitureSwivelChair);
        return new ResponseEntity<>(persistentFurnitureSwivelChair, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<FurnitureSwivelChair> updateSwivelChair(@RequestBody FurnitureSwivelChair furnitureSwivelChair) {
        final FurnitureSwivelChair persistentFurnitureSwivelChair = furnitureSwivelChairService.update(furnitureSwivelChair);
        return new ResponseEntity<>(persistentFurnitureSwivelChair, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteSwivelChairById(@PathVariable("id") Long id) {
        furnitureSwivelChairService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
