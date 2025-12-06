package ro.sapientia.furniture.model.dto;

/**
 * Represents a furniture element with its calculated position on the cutting sheet.
 */
public class PlacedElementDTO {
    
    private Long id;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    
    public PlacedElementDTO() {
    }
    
    public PlacedElementDTO(Long id, Integer x, Integer y, Integer width, Integer height) {
        this.id = id;
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
        return "PlacedElement [id=" + id + ", x=" + x + ", y=" + y +
               ", width=" + width + ", height=" + height + "]";
    }
}
