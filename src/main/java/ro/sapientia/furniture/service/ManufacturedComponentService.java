package ro.sapientia.furniture.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.dto.ManufacturedComponentDTO;
import ro.sapientia.furniture.exception.ResourceNotFoundException;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.ManufacturedComponent;
import ro.sapientia.furniture.model.ManufacturedComponentType;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.ManufacturedComponentRepository;
import ro.sapientia.furniture.repository.ManufacturedComponentTypeRepository;

@Service
@Transactional
public class ManufacturedComponentService {

    private final ManufacturedComponentRepository componentRepository;
    private final ManufacturedComponentTypeRepository typeRepository;
    private final ComponentListRepository componentListRepository;

    public ManufacturedComponentService(ManufacturedComponentRepository componentRepository,
                                        ManufacturedComponentTypeRepository typeRepository,
                                        ComponentListRepository componentListRepository) {
        this.componentRepository = componentRepository;
        this.typeRepository = typeRepository;
        this.componentListRepository = componentListRepository;
    }

    public List<ManufacturedComponent> findAll() {
        return componentRepository.findAll();
    }

    public ManufacturedComponent findById(Long id) {
        return componentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ManufacturedComponent not found with id " + id));
    }

    public ManufacturedComponent create(ManufacturedComponentDTO dto) {
        ComponentList componentList = componentListRepository.findById(dto.getComponentListId())
            .orElseThrow(() -> new ResourceNotFoundException("ComponentList not found with id " + dto.getComponentListId()));

        ManufacturedComponentType type = typeRepository.findById(dto.getManufacturedComponentTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("ManufacturedComponentType not found with id " + dto.getManufacturedComponentTypeId()));

        ManufacturedComponent mc = new ManufacturedComponent();
        mc.setComponentList(componentList);
        mc.setManufacturedComponentType(type);
        mc.setQuantity(dto.getQuantity());
        mc.setCreatedAt(LocalDateTime.now());
        mc.setUpdatedAt(LocalDateTime.now());

        return componentRepository.save(mc);
    }

    public ManufacturedComponent update(Long id, ManufacturedComponentDTO dto) {
        ManufacturedComponent existing = findById(id);

        if (dto.getComponentListId() != null) {
            ComponentList componentList = componentListRepository.findById(dto.getComponentListId())
                .orElseThrow(() -> new ResourceNotFoundException("ComponentList not found with id " + dto.getComponentListId()));
            existing.setComponentList(componentList);
        }

        if (dto.getManufacturedComponentTypeId() != null) {
            ManufacturedComponentType type = typeRepository.findById(dto.getManufacturedComponentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ManufacturedComponentType not found with id " + dto.getManufacturedComponentTypeId()));
            existing.setManufacturedComponentType(type);
        }

        if (dto.getQuantity() != null) {
            existing.setQuantity(dto.getQuantity());
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return componentRepository.save(existing);
    }

    public void delete(Long id) {
        ManufacturedComponent existing = findById(id);
        componentRepository.delete(existing);
    }

    public List<ManufacturedComponent> findByComponentListId(Long componentListId) {
        return componentRepository.findByComponentListId(componentListId);
    }

    public List<ManufacturedComponent> findByTypeId(Long typeId) {
        return componentRepository.findByManufacturedComponentTypeId(typeId);
    }
}