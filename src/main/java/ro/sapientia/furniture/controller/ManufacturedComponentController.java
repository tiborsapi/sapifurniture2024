package ro.sapientia.furniture.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.sapientia.furniture.dto.ManufacturedComponentDTO;
import ro.sapientia.furniture.model.ManufacturedComponent;
import ro.sapientia.furniture.service.ManufacturedComponentService;

@RestController
@RequestMapping("/api/manufactured-components")
public class ManufacturedComponentController {

    private final ManufacturedComponentService service;

    public ManufacturedComponentController(ManufacturedComponentService service) {
        this.service = service;
    }

    @GetMapping
    public List<ManufacturedComponent> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturedComponent> get(@PathVariable Long id) {
        ManufacturedComponent mc = service.findById(id);
        if (mc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mc);
    }

    @PostMapping
    public ResponseEntity<ManufacturedComponent> create(@Valid @RequestBody ManufacturedComponentDTO dto) {
        ManufacturedComponent created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturedComponent> update(@PathVariable Long id, @Valid @RequestBody ManufacturedComponentDTO dto) {
        ManufacturedComponent updated = service.update(id, dto);
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

    @GetMapping("/by-component-list/{componentListId}")
    public List<ManufacturedComponent> byComponentList(@PathVariable Long componentListId) {
        return service.findByComponentListId(componentListId);
    }

    @GetMapping("/by-type/{typeId}")
    public List<ManufacturedComponent> byType(@PathVariable Long typeId) {
        return service.findByTypeId(typeId);
    }
}