package ro.sapientia.furniture.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;

@Entity(name = "tv_stand")
public class Tvstand implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_tv_stand")
    @SequenceGenerator(name="pk_tv_stand",sequenceName="pk_tv_stand")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Min(value = 0, message = "Width must be positive")
    @Column(name = "width")
    private int width;

    @Min(value = 0, message = "Height must be positive")
    @Column(name = "heigth")
    private int heigth;

    @Min(value = 0, message = "Depth must be positive")
    @Column(name = "depth")
    private int depth;

    @Min(value = 0, message = "Doors must be positive")
    @Column(name = "doors")
    private int doors;

    @Column(name = "material")
    private String material;



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

    public int getDoors() { return doors; }

    public void setDoors(int doors) { this.doors = doors; }

    public String getMaterial() { return material; }

    public void setMaterial(String material) { this.material = material; }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "TvStand [id=" + id + ", width=" + width + ", heigth=" + heigth + ", depth=" + depth +  ", doors=" + doors + ", material=" + material +"]";
    }

}
