package ro.sapientia.cupboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "cupboard")
public class Cupboard implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_cupboard")
    @SequenceGenerator(name="pk_cupboard",sequenceName="pk_cupboard")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "numberOfCab")
    private int numberOfCab;

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