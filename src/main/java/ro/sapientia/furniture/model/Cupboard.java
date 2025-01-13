package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;

@Entity(name = "cupboard")
public class Cupboard implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_cupboard")
    @SequenceGenerator(name="pk_cupboard",sequenceName="pk_cupboard")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Min(value = 0, message = "Width must be positive")
    @Column(name = "width")
    private int width;

    @Min(value = 0, message = "Height must be positive")
    @Column(name = "height")
    private int height;

    @Min(value = 0, message = "Number of cabinets must be positive")
    @Column(name = "numberOfCab")
    private int numberOfCab;

    @Min(value = 0, message = "Number of drawers must be positive")
    @Column(name = "numberOfDrawer")
    private int numberOfDrawer;

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

    public int getNumberOfCab() {
        return numberOfCab;
    }

    public void setNumberOfCab(int numberOfCab) {
        this.numberOfCab = numberOfCab;
    }

    public int getNumberOfDrawer() {
        return numberOfDrawer;
    }

    public void setNumberOfDrawer(int numberOfDrawer) {
        this.numberOfDrawer = numberOfDrawer;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Cupboard{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", numberOfCab=" + numberOfCab +
                ", numberOfDrawer=" + numberOfDrawer +
                '}';
    }
}