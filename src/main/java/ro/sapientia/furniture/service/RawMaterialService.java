package ro.sapientia.furniture.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.repository.RawMaterialRepository;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Transactional
    public RawMaterial findOrCreateAndIncreaseQuantity(RawMaterial candidate) {
        // Try to find existing by type + dims
        Optional<RawMaterial> existingOpt = rawMaterialRepository.findByRawMaterialTypeAndLengthAndWidthAndHeight(
            candidate.getRawMaterialType(), candidate.getLength(), candidate.getWidth(), candidate.getHeight());

        if (existingOpt.isPresent()) {
            RawMaterial existing = existingOpt.get();
            // Increase quantity and update timestamp
            existing.setQuantity(existing.getQuantity() + candidate.getQuantity());
            existing.setUpdatedAt(LocalDateTime.now());
            return rawMaterialRepository.save(existing);
        } else {
            if (candidate.getCreatedAt() == null) {
                candidate.setCreatedAt(LocalDateTime.now());
            }
            if (candidate.getUpdatedAt() == null) {
                candidate.setUpdatedAt(LocalDateTime.now());
            }
            return rawMaterialRepository.save(candidate);
        }
    }
}
