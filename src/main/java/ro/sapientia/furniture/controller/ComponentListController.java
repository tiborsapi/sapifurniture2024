package ro.sapientia.furniture.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.service.PersistenceService;

@RestController
@RequestMapping("/api")
public class ComponentListController {

    private final PersistenceService persistenceService;
    private final FurnitureMapper furnitureMapper;
    private final ComponentListRepository componentListRepository;

    public ComponentListController(PersistenceService persistenceService,
                                   FurnitureMapper furnitureMapper,
                                   ComponentListRepository componentListRepository) {
        this.persistenceService = persistenceService;
        this.furnitureMapper = furnitureMapper;
        this.componentListRepository = componentListRepository;
    }

    /**
     * Create furniture (from editor) and persist a component list with raw materials.
     * Request param createdBy is expected (user id who created the component list).
     */
    @PostMapping("/furniture")
    public ResponseEntity<ComponentListDTO> createFurnitureAndComponentList(@RequestBody FurnitureBodyDTO furnitureDto,
                                                                            @RequestParam Long createdBy) {
        FurnitureBody fb = furnitureMapper.toEntity(furnitureDto);
        FurnitureBody savedFb = persistenceService.saveFurnitureBodyAndComponents(fb, createdBy);

        List<ComponentList> cls = componentListRepository.findByFurnitureBody_Id(savedFb.getId());
        if (cls.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ComponentList cl = cls.get(cls.size() - 1);
        ComponentListDTO response = furnitureMapper.toDto(cl);
        return ResponseEntity.ok(response);
    }

    /** GET all component lists */
    @GetMapping("/component-lists")
    public ResponseEntity<List<ComponentListDTO>> getAllComponentLists() {
        List<ComponentList> cls = componentListRepository.findAll();
        List<ComponentListDTO> dtos = cls.stream().map(furnitureMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /** GET component list by id */
    @GetMapping("/component-lists/{id}")
    public ResponseEntity<ComponentListDTO> getComponentListById(@PathVariable Long id) {
        return componentListRepository.findById(id)
            .map(furnitureMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
