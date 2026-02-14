package ro.sapientia.furniture.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name ="furniture_bed")
public class FurnitureBed implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_furniture_bed")
    @SequenceGenerator(name="pk_furniture_bed",sequenceName="pk_furniture_bed")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "length")
    private int length;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BedType type;

    @Column(name = "wood")
    @Enumerated(EnumType.STRING)
    private WoodType wood;

    public FurnitureBed() {
    }

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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BedType getType() {
        return type;
    }

    public void setType(BedType type) {
        this.type = type;
    }

    public WoodType getWood() {
        return wood;
    }

    public void setWood(WoodType wood) {
        this.wood = wood;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "FurnitureBed{" +
                "id=" + id +
                ", width=" + width +
                ", heigth=" + height +
                ", length=" + length +
                ", type=" + type +
                ", wood=" + wood +
                '}';
    }
}
