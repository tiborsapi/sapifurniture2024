package ro.sapientia.furniture.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialTypeRepository;

@Service
public class RawMaterialTypeService {

    private final RawMaterialTypeRepository rawMaterialTypeRepository;

    public RawMaterialTypeService(RawMaterialTypeRepository rawMaterialTypeRepository) {
        this.rawMaterialTypeRepository = rawMaterialTypeRepository;
    }

    @Transactional
    public RawMaterialType findOrCreateByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return rawMaterialTypeRepository.findByName(name.trim())
            .orElseGet(() -> {
                RawMaterialType rmt = new RawMaterialType(name.trim());
                return rawMaterialTypeRepository.save(rmt);
            });
    }
}
