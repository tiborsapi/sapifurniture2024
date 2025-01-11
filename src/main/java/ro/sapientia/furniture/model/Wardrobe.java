package ro.sapientia.furniture.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "wardrobe")
public class Wardrobe extends FurnitureBody {

	private static final long serialVersionUID = 1L;

	@Column(name = "number_of_doors")
	private int numberOfDoors;

	@Column(name = "has_mirror")
	private boolean hasMirror;

	@Column(name = "number_of_shelves")
	private int numberOfShelves;

	public Wardrobe() {
	}

	public int getNumberOfDoors() {
		return numberOfDoors;
	}

	public void setNumberOfDoors(int numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}

	public boolean isHasMirror() {
		return hasMirror;
	}

	public void setHasMirror(boolean hasMirror) {
		this.hasMirror = hasMirror;
	}

	public int getNumberOfShelves() {
		return numberOfShelves;
	}

	public void setNumberOfShelves(int numberOfShelves) {
		this.numberOfShelves = numberOfShelves;
	}

	public double calculateShelfHeight() {
		if (numberOfShelves <= 0) {
			return 0;
		}
		return getHeigth() / (double) numberOfShelves;
	}

	@Override
	public String toString() {
		return "Wardrobe [id=" + getId() + ", width=" + getWidth() + ", height=" + getHeigth() + ", depth=" + getDepth()
				+ ", numberOfDoors=" + numberOfDoors + ", hasMirror=" + hasMirror + ", numberOfShelves="
				+ numberOfShelves + "]";
	}
}
