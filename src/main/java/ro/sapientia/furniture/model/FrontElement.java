package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.util.LinkedList;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import javax.persistence.FetchType;

@Entity(name = "front_element")
public class FrontElement implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ElementType {
		BODY, DRAWER, DOOR, UNKNOWN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_front_element")
	@SequenceGenerator(name="pk_front_element", sequenceName="pk_front_element") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "furniture_body_id", nullable = false)
	private FurnitureBody furnitureBody;

	@Enumerated(EnumType.STRING)
	@Column(name = "element_type", nullable = false)
	private ElementType elementType;

	@Column(name = "pos_x", nullable = false)
	private Integer posX;

	@Column(name = "pos_y", nullable = false)
	private Integer posY;

	@Column(name = "width", nullable = false)
	private Integer width;

	@Column(name = "height", nullable = false)
	private Integer height;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;

	@ManyToOne
	@JoinColumn(name = "raw_material_type_id", nullable = true)
	private RawMaterialType rawMaterialType;

	@OneToOne(mappedBy = "parent")
	private Separator separator;

	// Constructors
	public FrontElement() {}

	public FrontElement(FurnitureBody furnitureBody, ElementType elementType, 
					   Integer posX, Integer posY, Integer width, Integer height) {
		this.furnitureBody = furnitureBody;
		this.elementType = elementType;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}

	public FrontElement(FurnitureBody furnitureBody, ElementType elementType, 
					   Integer posX, Integer posY, Integer width, Integer height, 
					   String details) {
		this(furnitureBody, elementType, posX, posY, width, height);
		this.details = details;
	}

	public FrontElement(FurnitureBody furnitureBody, ElementType elementType, 
					   Integer posX, Integer posY, Integer width, Integer height, 
					   String details, RawMaterialType rawMaterialType) {
		this(furnitureBody, elementType, posX, posY, width, height, details);
		this.rawMaterialType = rawMaterialType;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FurnitureBody getFurnitureBody() {
		return furnitureBody;
	}

	public void setFurnitureBody(FurnitureBody furnitureBody) {
		this.furnitureBody = furnitureBody;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public RawMaterialType getRawMaterialType() {
		return rawMaterialType;
	}

	public void setRawMaterialType(RawMaterialType rawMaterialType) {
		this.rawMaterialType = rawMaterialType;
	}

	public Separator getSeparator() {
		return separator;
	}

	public void setSeparator(Separator separator) {
		this.separator = separator;
	}

	public LinkedList<RawMaterial> getMaterials() {
		if (separator == null) {
			LinkedList<RawMaterial> elementMaterials  = new LinkedList<RawMaterial>();
			elementMaterials.add(getOppositeMaterial());
			return elementMaterials;
		} else {
			return separator.getMaterials();
		}
	}

	public RawMaterial getHorizontalMaterial() {
		return new RawMaterial(
			furnitureBody.getThickness(),
			width,
			furnitureBody.getDepth(),
			rawMaterialType,
			1
		);
	}

	public RawMaterial getVerticalMaterial() {
		return new RawMaterial(
			height,
			furnitureBody.getThickness(),
			furnitureBody.getDepth(),
			rawMaterialType,
			1
		);
	}

	public RawMaterial getOppositeMaterial() {
		return new RawMaterial(
			height,
			width,
			furnitureBody.getThickness(),
			rawMaterialType,
			1
		);
	}

	@Override
	public String toString() {
		return "FrontElement [id=" + id + ", elementType=" + elementType + 
			   ", posX=" + posX + ", posY=" + posY + ", width=" + width + 
			   ", height=" + height + ", details=" + details + "]";
	}
}