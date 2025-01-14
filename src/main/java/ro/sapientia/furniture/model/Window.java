package ro.sapientia.furniture.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity(name = "window")
public class Window implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_window")
    @SequenceGenerator(name = "pk_window", sequenceName = "pk_window")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "glassType")
    private String glassType;

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGlassType() {
        return glassType;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }

    @Override
    public String toString() {
        return "Window [id=" + id + ", width=" + width + ", height=" + height + ", glassType=" + glassType + "]";
    }
}
