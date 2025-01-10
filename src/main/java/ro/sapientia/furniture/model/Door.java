package ro.sapientia.furniture.model;

import javax.persistence.*;

@Entity(name = "door")
public class Door extends FurnitureBody {
    private static final long serialVersionUID = 1L;

    @Column(name = "material")
    private String material; 
    
    @Column(name = "color")
    private String color;

    @Column(name = "has_glass")
    private boolean hasGlass;
    
    @Column(name ="numberOfGlassPanels")
    private int numberOfGlassPanels;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
    public String getColor() {
    	return color;
    }
    public void setColor(String color) {
    	this.color = color;
    }

    public boolean isHasGlass() {
        return hasGlass;
    }
    public void setNumberOfGlassPanels(int numberOfGlassPanels) {
    	this.numberOfGlassPanels = numberOfGlassPanels;
    }
    public int getNumberOfGlassPanels() {
    	return numberOfGlassPanels;
    }

    public void setHasGlass(boolean hasGlass) {
        this.hasGlass = hasGlass;
    }

    @Override
    public String toString() {
        return "Door [id=" + getId() + ", width=" + getWidth() + ", heigth=" + getHeigth() + 
               ", depth=" + getDepth() + ", material=" + material + "color="+ color +", hasGlass=" + hasGlass + ", numberOfGlassPanels =" + numberOfGlassPanels +  "]";
    }
}
