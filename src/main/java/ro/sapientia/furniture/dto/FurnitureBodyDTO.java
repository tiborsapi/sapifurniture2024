package ro.sapientia.furniture.dto;

import java.util.List;

public class FurnitureBodyDTO {
    private Integer width;
    private Integer heigth;
    private Integer depth;
    private Integer thickness;
    private List<FrontElementDTO> frontElements;

    // getters / setters

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }

    public Integer getHeigth() { return heigth; }
    public void setHeigth(Integer heigth) { this.heigth = heigth; }

    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    public Integer getThickness() { return thickness; }
    public void setThickness(Integer thickness) { this.thickness = thickness; }

    public List<FrontElementDTO> getFrontElements() { return frontElements; }
    public void setFrontElements(List<FrontElementDTO> frontElements) { this.frontElements = frontElements; }

    public static class FrontElementDTO {
        private String elementType;
        private Integer posX;
        private Integer posY;
        private Integer width;
        private Integer height;
        private String details;
        private String rawMaterialTypeName;

        // getters / setters
        public String getElementType() { return elementType; }
        public void setElementType(String elementType) { this.elementType = elementType; }

        public Integer getPosX() { return posX; }
        public void setPosX(Integer posX) { this.posX = posX; }

        public Integer getPosY() { return posY; }
        public void setPosY(Integer posY) { this.posY = posY; }

        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }

        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }

        public String getDetails() { return details; }
        public void setDetails(String details) { this.details = details; }

        public String getRawMaterialTypeName() { return rawMaterialTypeName; }
        public void setRawMaterialTypeName(String rawMaterialTypeName) { this.rawMaterialTypeName = rawMaterialTypeName; }
    }
}
