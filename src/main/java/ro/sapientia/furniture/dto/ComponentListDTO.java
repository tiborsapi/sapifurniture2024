package ro.sapientia.furniture.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ComponentListDTO {
    private Long id;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long furnitureBodyId;
    private List<RawMaterialDTO> rawMaterials;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getFurnitureBodyId() { return furnitureBodyId; }
    public void setFurnitureBodyId(Long furnitureBodyId) { this.furnitureBodyId = furnitureBodyId; }

    public List<RawMaterialDTO> getRawMaterials() { return rawMaterials; }
    public void setRawMaterials(List<RawMaterialDTO> rawMaterials) { this.rawMaterials = rawMaterials; }
}
