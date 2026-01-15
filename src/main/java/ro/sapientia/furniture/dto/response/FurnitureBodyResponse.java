package ro.sapientia.furniture.dto.response;

public class FurnitureBodyResponse {

    private Long id;
    private int width;
    private int heigth;
    private int depth;

    // Constructors
    public FurnitureBodyResponse() {
    }

    public FurnitureBodyResponse(Long id, int width, int heigth, int depth) {
        this.id = id;
        this.width = width;
        this.heigth = heigth;
        this.depth = depth;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "FurnitureBodyResponse{" +
                "id=" + id +
                ", width=" + width +
                ", heigth=" + heigth +
                ", depth=" + depth +
                '}';
    }
}
