package ro.sapientia.furniture.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "furniture_table")
public class FurnitureTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_furniture_table")
    @SequenceGenerator(name = "pk_furniture_table", sequenceName = "pk_furniture_table")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "depth")
    private int depth;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private FurnitureColor color;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TableType type;

    public FurnitureTable() {

    }

    public Long getId() {
        return this.id;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDepth() {
        return this.depth;
    }

    public FurnitureColor getColor() {
        return this.color;
    }

    public TableType getType() {
        return this.type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setColor(FurnitureColor color) {
        this.color = color;
    }

    public void setType(TableType type) {
        this.type = type;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FurnitureTable)) return false;
        final FurnitureTable other = (FurnitureTable) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.getWidth() != other.getWidth()) return false;
        if (this.getHeight() != other.getHeight()) return false;
        if (this.getDepth() != other.getDepth()) return false;
        final Object this$color = this.getColor();
        final Object other$color = other.getColor();
        if (this$color == null ? other$color != null : !this$color.equals(other$color)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FurnitureTable;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + this.getWidth();
        result = result * PRIME + this.getHeight();
        result = result * PRIME + this.getDepth();
        final Object $color = this.getColor();
        result = result * PRIME + ($color == null ? 43 : $color.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "FurnitureTable(id=" + this.getId() + ", width=" + this.getWidth() + ", heigth=" + this.getHeight() + ", depth=" + this.getDepth() + ", color=" + this.getColor() + ", type=" + this.getType() + ")";
    }
}
