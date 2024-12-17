package ro.sapientia.furniture.dto;

public class StopFurnitureRequestDTO {

    private long furnitureBodyId;
    private long furnitureStopperId;

    public StopFurnitureRequestDTO(long furnitureBodyId, long furnitureStopperId) {
        this.furnitureBodyId = furnitureBodyId;
        this.furnitureStopperId = furnitureStopperId;
    }

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