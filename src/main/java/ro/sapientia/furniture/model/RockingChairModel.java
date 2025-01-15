package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "rocking_chair")
public class RockingChairModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public RockingChairModel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_rocking_chair")
    @SequenceGenerator(name = "pk_rocking_chair", sequenceName = "pk_rocking_chair")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "depth")
    private int depth;

    @Column(name = "material")
    private String material;

    @Column(name = "rocker_radius")
    private double rockerRadius;

    @Column(name = "seat_height")
    private int seatHeight;

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

    public double getRockerRadius() {
        return rockerRadius;
    }

    public void setRockerRadius(double rockerRadius) {
        this.rockerRadius = rockerRadius;
    }

    public int getSeatHeight() {
        return seatHeight;
    }

    public void setSeatHeight(int seatHeight) {
        this.seatHeight = seatHeight;
    }

    @Override
    public String toString() {
        return "RockingChair [id=" + id + ", width=" + width + ", height=" + height + ", depth=" + depth +
                ", material=" + material + ", rockerRadius=" + rockerRadius + ", seatHeight=" + seatHeight + "]";
    }
}
