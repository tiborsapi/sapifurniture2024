package ro.sapientia.furniture.dto;

import java.util.List;

public class FurnitureBodyDTO {
    private Long id;
    private Integer width;
    private Integer heigth;
    private Integer depth;
    private Integer thickness;
    private List<FrontElementDTO> frontElements;
    private Long mainFrontElementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeigth() {
        return heigth;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getThickness() {
        return thickness;
    }

    public void setThickness(Integer thickness) {
        this.thickness = thickness;
    }

    public List<FrontElementDTO> getFrontElements() {
        return frontElements;
    }

    public void setFrontElements(List<FrontElementDTO> frontElements) {
        this.frontElements = frontElements;
    }

    public Long getMainFrontElementId() {
        return mainFrontElementId;
    }

    public void setMainFrontElementId(Long mainFrontElementId) {
        this.mainFrontElementId = mainFrontElementId;
    }
}
