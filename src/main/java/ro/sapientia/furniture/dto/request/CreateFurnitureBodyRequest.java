package ro.sapientia.furniture.dto.request;

import javax.validation.constraints.Min;

public class CreateFurnitureBodyRequest {

    @Min(value = 1, message = "Width must be greater than 0")
    private int width;

    @Min(value = 1, message = "Height must be greater than 0")
    private int heigth;

    @Min(value = 1, message = "Depth must be greater than 0")
    private int depth;

    // -------- getters / setters --------

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
