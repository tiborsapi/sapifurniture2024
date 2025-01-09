package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "wooden_trunk")
public class WoodenTrunk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_wooden_trunk")
    @SequenceGenerator(name = "pk_wooden_trunk", sequenceName = "pk_wooden_trunk", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

    @Column(name = "depth")
    private int depth;

    @Column(name = "material")
    private String material;

    @Column(name = "has_lid")
    private boolean hasLid;

    @Column(name = "capacity")
    private int capacity;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isHasLid() {
        return hasLid;
    }

    public void setHasLid(boolean hasLid) {
        this.hasLid = hasLid;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "WoodenTrunk [id=" + id + ", height=" + height + ", width=" + width +
                ", depth=" + depth + ", material=" + material + ", hasLid=" + hasLid +
                ", capacity=" + capacity + "]";
    }
}