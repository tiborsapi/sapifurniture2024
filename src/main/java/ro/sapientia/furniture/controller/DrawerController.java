package ro.sapientia.furniture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.model.Drawer;
import ro.sapientia.furniture.service.DrawerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/drawers")
@RequiredArgsConstructor
public class DrawerController {
    private final DrawerService drawerService;

    @PostMapping
    public ResponseEntity<Drawer> createDrawer(@Valid @RequestBody Drawer drawer) {
        return ResponseEntity.ok(drawerService.createDrawer(drawer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drawer> updateDrawer(@PathVariable Long id,
                                               @Valid @RequestBody Drawer drawer) {
        return ResponseEntity.ok(drawerService.updateDrawer(id, drawer));
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<Void> openDrawer(@PathVariable Long id) {
        drawerService.openDrawer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Void> closeDrawer(@PathVariable Long id) {
        drawerService.closeDrawer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/slide")
    public ResponseEntity<Void> slideDrawer(@PathVariable Long id,
                                            @RequestParam Double distance) {
        drawerService.slideDrawer(id, distance);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/material/{material}")
    public ResponseEntity<List<Drawer>> getByMaterial(@PathVariable String material) {
        return ResponseEntity.ok(drawerService.findByMaterial(material));
    }

    @GetMapping("/open")
    public ResponseEntity<List<Drawer>> getOpenDrawers() {
        return ResponseEntity.ok(drawerService.findOpenDrawers());
    }

    @GetMapping("/capacity")
    public ResponseEntity<List<Drawer>> getByMinimumCapacity(@RequestParam Double minCapacity) {
        return ResponseEntity.ok(drawerService.findByMinimumCapacity(minCapacity));
    }

    @GetMapping("/dimensions")
    public ResponseEntity<List<Drawer>> getByDimensions(@RequestParam Double width,
                                                        @RequestParam Double height,
                                                        @RequestParam Double depth) {
        return ResponseEntity.ok(drawerService.findByDimensions(width, height, depth));
    }
}