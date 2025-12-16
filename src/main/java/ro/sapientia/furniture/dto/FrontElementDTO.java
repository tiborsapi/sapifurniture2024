package ro.sapientia.furniture.dto;

public class FrontElementDTO {
    private Long id;
    private Long furnitureBodyId;
    private String elementType;
    private Integer posX;
    private Integer posY;
    private Integer width;
    private Integer height;
    private String details;
    private String rawMaterialTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFurnitureBodyId() {
        return furnitureBodyId;
    }

    public void setFurnitureBodyId(Long furnitureBodyId) {
        this.furnitureBodyId = furnitureBodyId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRawMaterialTypeName() {
        return rawMaterialTypeName;
    }

    public void setRawMaterialTypeName(String rawMaterialTypeName) {
        this.rawMaterialTypeName = rawMaterialTypeName;
    }
}

