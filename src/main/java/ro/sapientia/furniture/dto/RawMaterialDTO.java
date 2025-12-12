package ro.sapientia.furniture.dto;

import java.time.LocalDateTime;

public class RawMaterialDTO {
    private Long id;
    private Integer height;
    private Integer width;
    private Integer length;
    private Integer quantity;
    private String rawMaterialTypeName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getRawMaterialTypeName() { return rawMaterialTypeName; }
    public void setRawMaterialTypeName(String rawMaterialTypeName) { this.rawMaterialTypeName = rawMaterialTypeName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
