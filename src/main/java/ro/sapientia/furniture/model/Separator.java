package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "separator")
public class Separator implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum SeparatorType {
		HORIZONTAL, VERTICAL
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_separator")
	@SequenceGenerator(name="pk_separator", sequenceName="pk_separator") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "parent_id", nullable = false)
	private FrontElement parent;

	@Enumerated(EnumType.STRING)
	@Column(name = "separator_type", nullable = false)
	private SeparatorType separatorType;

	@Column(name = "relative_position", nullable = false)
	private Double relativePosition;

	@Column(name = "pos_x", nullable = false)
	private Integer posX;

	@Column(name = "pos_y", nullable = false)
	private Integer posY;

	@Column(name = "width", nullable = false)
	private Integer width;

	@Column(name = "height", nullable = false)
	private Integer height;

	@OneToOne
	@JoinColumn(name = "first_front_element_id")
	private FrontElement firstFrontElement;

	@OneToOne
	@JoinColumn(name = "second_front_element_id")
	private FrontElement secondFrontElement;

	// Constructors
	public Separator() {}

	public Separator(FrontElement parent, SeparatorType separatorType, 
					Double relativePosition, Integer posX, Integer posY, 
					Integer width, Integer height, FrontElement firstFrontElement, 
					FrontElement secondFrontElement) {
		this.parent = parent;
		this.separatorType = separatorType;
		this.relativePosition = relativePosition;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.firstFrontElement = firstFrontElement;
		this.secondFrontElement = secondFrontElement;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FrontElement getParent() {
		return parent;
	}

	public void setParent(FrontElement parent) {
		this.parent = parent;
	}

	public SeparatorType getSeparatorType() {
		return separatorType;
	}

	public void setSeparatorType(SeparatorType separatorType) {
		this.separatorType = separatorType;
	}

	public Double getRelativePosition() {
		return relativePosition;
	}

	public void setRelativePosition(Double relativePosition) {
		this.relativePosition = relativePosition;
	}

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public FrontElement getFirstFrontElement() {
		return firstFrontElement;
	}

	public void setFirstFrontElement(FrontElement firstFrontElement) {
		this.firstFrontElement = firstFrontElement;
	}

	public FrontElement getSecondFrontElement() {
		return secondFrontElement;
	}

	public void setSecondFrontElement(FrontElement secondFrontElement) {
		this.secondFrontElement = secondFrontElement;
	}

	public LinkedList<RawMaterial> getRawMaterials() {
		LinkedList<RawMaterial> elementMaterials  = new LinkedList<RawMaterial>();

		if (separatorType == SeparatorType.HORIZONTAL) {
			elementMaterials.add(firstFrontElement.getVerticalMaterial());
		} else {
			elementMaterials.add(firstFrontElement.getHorizontalMaterial());
		}
		if (firstFrontElement.getSeparator() != null) {
			elementMaterials.addAll(firstFrontElement.getSeparator().getRawMaterials());
		}
		if (secondFrontElement.getSeparator() != null) {
			elementMaterials.addAll(secondFrontElement.getSeparator().getRawMaterials());
		}

		return elementMaterials;
	}

	@Override
	public String toString() {
		return "Separator [id=" + id + ", separatorType=" + separatorType + 
			   ", relativePosition=" + relativePosition + ", posX=" + posX + 
			   ", posY=" + posY + ", width=" + width + ", height=" + height + 
			   ", firstFrontElement=" + (firstFrontElement != null ? firstFrontElement.getId() : null) + 
			   ", secondFrontElement=" + (secondFrontElement != null ? secondFrontElement.getId() : null) + "]";
	}
}