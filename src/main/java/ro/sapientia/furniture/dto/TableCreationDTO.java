package ro.sapientia.furniture.dto;

import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.util.EnumConverter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class TableCreationDTO implements Serializable {

    @Positive
    Integer width;

    @Positive
    Integer height;

    @Positive
    Integer depth;

    @NotEmpty
    String color;

    @NotEmpty
    String type;

    public TableCreationDTO() {

    }

    public @NotEmpty String getType() {
        return type;
    }

    public void setType(@NotEmpty String type) {
        this.type = type;
    }

    public @NotEmpty String getColor() {
        return color;
    }

    public void setColor(@NotEmpty String color) {
        this.color = color;
    }

    public @Positive Integer getDepth() {
        return depth;
    }

    public void setDepth(@Positive Integer depth) {
        this.depth = depth;
    }

    public @Positive Integer getHeight() {
        return height;
    }

    public void setHeight(@Positive Integer height) {
        this.height = height;
    }

    public @Positive Integer getWidth() {
        return width;
    }

    public void setWidth(@Positive Integer width) {
        this.width = width;
    }

    public FurnitureTable mapToModel() throws Exception {

        FurnitureTable table = new FurnitureTable();

        table.setWidth(width);
        table.setHeight(height);
        table.setDepth(depth);

        table.setColor(EnumConverter.tableColorEnumConverter(color));
        table.setType(EnumConverter.tableTypeEnumConverter(type));

        return table;
    }
}
