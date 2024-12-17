package ro.sapientia.furniture.dto;

public class StopFurnitureRequestDTO {

    private long furnitureBodyId;
    private long furnitureStopperId;

    public long getFurnitureBodyId() {
        return furnitureBodyId;
    }

    public void setFurnitureBodyId(long furnitureBodyId) {
        this.furnitureBodyId = furnitureBodyId;
    }

    public long getFurnitureStopperId() {
        return furnitureStopperId;
    }

    public void setFurnitureStopperId(long furnitureStopperId) {
        this.furnitureStopperId = furnitureStopperId;
    }
}