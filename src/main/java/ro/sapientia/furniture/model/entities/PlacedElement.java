package ro.sapientia.furniture.model.entities;

/**
 * Domain model representing a furniture element with its calculated position on the cutting sheet.
 */
public class PlacedElement {

    private Long id;
    private String type;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;

    public PlacedElement() {
    }

    public PlacedElement(Long id, String type, Integer x, Integer y, Integer width, Integer height) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
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

    @Override
    public String toString() {
        return "PlacedElement [id=" + id + ", type=" + type + ", x=" + x + ", y=" + y +
               ", width=" + width + ", height=" + height + "]";
    }
}

