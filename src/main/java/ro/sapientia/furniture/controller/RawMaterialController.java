package ro.sapientia.furniture.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.repository.RawMaterialRepository;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialController(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @GetMapping("/all")
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }
}


