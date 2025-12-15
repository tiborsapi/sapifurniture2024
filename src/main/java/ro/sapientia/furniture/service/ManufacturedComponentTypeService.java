package ro.sapientia.furniture.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.exception.ResourceNotFoundException;
import ro.sapientia.furniture.model.ManufacturedComponentType;
import ro.sapientia.furniture.repository.ManufacturedComponentTypeRepository;

@Service
@Transactional
public class ManufacturedComponentTypeService {

    private final ManufacturedComponentTypeRepository repository;

    public ManufacturedComponentTypeService(ManufacturedComponentTypeRepository repository) {
        this.repository = repository;
    }

    public List<ManufacturedComponentType> findAll() {
        return repository.findAll();
    }

    public ManufacturedComponentType findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ManufacturedComponentType not found with id " + id));
    }

    public ManufacturedComponentType create(ManufacturedComponentType type) {
        // optional: ellenőrzés névre
        if (type.getName() != null && repository.existsByName(type.getName())) {
            throw new IllegalArgumentException("ManufacturedComponentType with name already exists: " + type.getName());
        }
        type.setCreatedAt(LocalDateTime.now());
        type.setUpdatedAt(LocalDateTime.now());
        return repository.save(type);
    }

    public ManufacturedComponentType update(Long id, ManufacturedComponentType input) {
        ManufacturedComponentType existing = findById(id);
        if (input.getName() != null) {
            existing.setName(input.getName());
        }
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void delete(Long id) {
        ManufacturedComponentType existing = findById(id);
        repository.delete(existing);
    }
}