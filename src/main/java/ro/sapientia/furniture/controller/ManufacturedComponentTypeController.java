package ro.sapientia.furniture.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.sapientia.furniture.model.ManufacturedComponentType;
import ro.sapientia.furniture.service.ManufacturedComponentTypeService;

@RestController
@RequestMapping("/api/manufactured-component-types")
public class ManufacturedComponentTypeController {

    private final ManufacturedComponentTypeService service;

    public ManufacturedComponentTypeController(ManufacturedComponentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ManufacturedComponentType> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturedComponentType> get(@PathVariable Long id) {
        ManufacturedComponentType type = service.findById(id);
        if (type == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(type);
    }
    

    @PostMapping
    public ResponseEntity<ManufacturedComponentType> create(@Valid @RequestBody ManufacturedComponentType type) {
        ManufacturedComponentType created = service.create(type);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturedComponentType> update(@PathVariable Long id, @Valid @RequestBody ManufacturedComponentType input) {
        ManufacturedComponentType updated = service.update(id, input);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}