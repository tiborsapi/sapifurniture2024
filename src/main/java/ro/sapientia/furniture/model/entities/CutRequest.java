package ro.sapientia.furniture.model.entities;

import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain model for cutting optimization request.
 * Contains the sheet dimensions and elements to be placed.
 */
public class CutRequest {

    private Integer sheetWidth;
    private Integer sheetHeight;
    private List<FurnitureBodyDTO> elements;

    public CutRequest() {
        this.elements = new ArrayList<>();
    }

    public CutRequest(Integer sheetWidth, Integer sheetHeight, List<FurnitureBodyDTO> elements) {
        this.sheetWidth = sheetWidth;
        this.sheetHeight = sheetHeight;
        this.elements = elements != null ? elements : new ArrayList<>();
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
        return "CutRequest [sheetWidth=" + sheetWidth + ", sheetHeight=" + sheetHeight +
               ", elements=" + elements + "]";
    }
}


