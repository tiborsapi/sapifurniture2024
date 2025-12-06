package ro.sapientia.furniture.model.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Request model for the cutting optimization endpoint.
 * Contains the sheet dimensions and elements to be placed.
 */
public class CutRequestDTO {

    @NotNull(message = "Sheet width is required")
    @Min(value = 1, message = "Sheet width must be positive")
    private Integer sheetWidth;

    @NotNull(message = "Sheet height is required")
    @Min(value = 1, message = "Sheet height must be positive")
    private Integer sheetHeight;

    @NotEmpty(message = "Elements list cannot be empty")
    @Valid
    private List<FurnitureBodyDTO> elements;

    public CutRequestDTO() {
    }

    public CutRequestDTO(Integer sheetWidth, Integer sheetHeight, List<FurnitureBodyDTO> elements) {
        this.sheetWidth = sheetWidth;
        this.sheetHeight = sheetHeight;
        this.elements = elements;
    }

    public Integer getSheetWidth() {
        return sheetWidth;
    }

    public void setSheetWidth(Integer sheetWidth) {
        this.sheetWidth = sheetWidth;
    }

    public Integer getSheetHeight() {
        return sheetHeight;
    }

    public void setSheetHeight(Integer sheetHeight) {
        this.sheetHeight = sheetHeight;
    }

    public List<FurnitureBodyDTO> getElements() {
        return elements;
    }

    public void setElements(List<FurnitureBodyDTO> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "CutRequest [sheetWidth=" + sheetWidth + ", sheetHeight=" + sheetHeight + ", elements=" + elements + "]";
    }
}
