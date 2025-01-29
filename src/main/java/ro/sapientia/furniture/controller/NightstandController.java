package ro.sapientia.furniture.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;
import ro.sapientia.furniture.service.NightstandService;
import ro.sapientia.furniture.util.EnumConverter;

@RestController
@RequestMapping("/nightstands")
public class NightstandController {

    private final NightstandService nightstandService;

    public NightstandController(final NightstandService nightstandService) {
        this.nightstandService = nightstandService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Nightstand>> getAllNightstands() {
        final List<Nightstand> nightstands = this.nightstandService.findAllNightstands();
        return new ResponseEntity<>(nightstands, HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Nightstand> getNightstandById(final @PathVariable("id") Long id) {
        final Nightstand nightstand = this.nightstandService.findNightstandById(id);

        if (null == nightstand) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(nightstand, HttpStatus.OK);
        }
    }

    @GetMapping("/find/color/{color}")
    public ResponseEntity<List<Nightstand>> getNightstandsByColor(final @PathVariable("color") String color) {
        NightstandColor nightstandColor;
        try {
            nightstandColor = EnumConverter.toNightstandColor(color);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final List<Nightstand> nightstands = this.nightstandService.findNightstandsByColor(nightstandColor);
        return new ResponseEntity<>(nightstands, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Nightstand> addNightstand(@RequestBody Nightstand nightstand) {
        final Nightstand persistentNightstand = this.nightstandService.create(nightstand);
        return new ResponseEntity<>(persistentNightstand, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNightstandById(@PathVariable("id") Long id) {
        try {
            this.nightstandService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
