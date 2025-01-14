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

import ro.sapientia.furniture.model.Window;
import ro.sapientia.furniture.service.WindowService;

@RestController
@RequestMapping("/window")
public class WindowController {

    private final WindowService windowService;

    public WindowController(final WindowService windowService) {
        this.windowService = windowService;
    }

    // GET all windows
    @GetMapping("/all")
    public ResponseEntity<List<Window>> getAllWindows() {
        final List<Window> windows = windowService.findAllWindows();
        return new ResponseEntity<>(windows, HttpStatus.OK);
    }

    // GET window by ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Window> getWindowById(@PathVariable("id") Long id) {
        final Window window = windowService.findWindowById(id);
        return new ResponseEntity<>(window, HttpStatus.OK);
    }

    // POST add a new window
    @PostMapping("/add")
    public ResponseEntity<Window> addWindow(@RequestBody Window window) {
        final Window persistWindow = windowService.create(window);
        return new ResponseEntity<>(persistWindow, HttpStatus.CREATED);
    }

    // POST update an existing window
    @PostMapping("/update")
    public ResponseEntity<Window> updateWindow(@RequestBody Window window) {
        final Window persistWindow = windowService.update(window);
        return new ResponseEntity<>(persistWindow, HttpStatus.OK);
    }

    // GET delete a window by ID
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteWindowById(@PathVariable("id") Long id) {
        windowService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
