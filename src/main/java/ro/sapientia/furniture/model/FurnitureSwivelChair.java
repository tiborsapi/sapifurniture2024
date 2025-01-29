package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "swivel_chair")
public class FurnitureSwivelChair implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_swivel_chair")
    @SequenceGenerator(name = "pk_swivel_chair", sequenceName = "pk_swivel_chair")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "seat_width")
    private int seatWidth;

    @Column(name = "seat_depth")
    private int seatDepth;

    @Column(name = "backrest_heigth")
    private int backrestHeigth;

    @Column(name = "weight_capacity")
    private int weightCapacity;

    @Column(name = "material")
    private String material;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeatWidth() {
        return seatWidth;
    }

    public void setSeatWidth(int seatWidth) {
        this.seatWidth = seatWidth;
    }

    public int getSeatDepth() {
        return seatDepth;
    }

    public void setSeatDepth(int seatDepth) {
        this.seatDepth = seatDepth;
    }

    public int getBackrestHeigth() {
        return backrestHeigth;
    }

    public void setBackrestHeigth(int backrestHeigth) {
        this.backrestHeigth = backrestHeigth;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString()
    {
        return "SwivelChair [id=" + id + ", seatWidth=" + seatWidth + ", seatDepth=" + seatDepth + ", backrestHeigth=" + backrestHeigth + ", weightCapacity=" + weightCapacity + ", material=" + material + "]";
    }
}
