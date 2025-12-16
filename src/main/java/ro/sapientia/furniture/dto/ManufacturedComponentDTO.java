package ro.sapientia.furniture.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ManufacturedComponentDTO {

    @NotNull
    private Long componentListId;

    @NotNull
    private Long manufacturedComponentTypeId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public ManufacturedComponentDTO() {}

    public ManufacturedComponentDTO(Long componentListId, Long manufacturedComponentTypeId, Integer quantity) {
        this.componentListId = componentListId;
        this.manufacturedComponentTypeId = manufacturedComponentTypeId;
        this.quantity = quantity;
    }

    public Long getComponentListId() {
        return componentListId;
    }

    public void setComponentListId(Long componentListId) {
        this.componentListId = componentListId;
    }

    public Long getManufacturedComponentTypeId() {
        return manufacturedComponentTypeId;
    }

    public void setManufacturedComponentTypeId(Long manufacturedComponentTypeId) {
        this.manufacturedComponentTypeId = manufacturedComponentTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}