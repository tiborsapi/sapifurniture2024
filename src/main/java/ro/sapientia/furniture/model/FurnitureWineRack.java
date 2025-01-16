package ro.sapientia.furniture.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "furniture_wine_rack")
public class FurnitureWineRack implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_furniture_wine_rack")
    @SequenceGenerator(name="pk_furniture_wine_rack",sequenceName="pk_furniture_wine_rack")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "width")
    private int width;

    @Column(name = "heigth")
    private int heigth;

    @Column(name = "depth")
    private int depth;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "current_load")
    private int currentLoad;

    @Column(name = "transparent_front")
    private boolean transparentFront;

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

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentLoad() { return currentLoad; }

    public void setCurrentLoad(int currentLoad) { this.currentLoad = currentLoad; }

    public boolean isTransparentFront() { return transparentFront; }

    public void setTransparentFront(boolean transparentFront) { this.transparentFront = transparentFront; }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "FurnitureBody [id=" + this.getId() + ", width=" + this.getWidth() + ", heigth=" + this.getHeigth() + ", depth=" + this.getDepth()
                + ", capacity=" + this.getCapacity() + ", currentLoad=" + this.getCurrentLoad() + ", transperant front view: " + this.isTransparentFront() + "]";

    }

}
