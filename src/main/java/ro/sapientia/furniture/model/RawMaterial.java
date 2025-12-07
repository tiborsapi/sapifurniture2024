package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "raw_materials")
@Table(name = "raw_materials", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"raw_material_type_id", "length", "width", "height"})
})
public class RawMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_raw_materials")
	@SequenceGenerator(name="pk_raw_materials", sequenceName="pk_raw_materials") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "height", nullable = false)
	private Integer height;

	@Column(name = "width", nullable = false)
	private Integer width;

	@Column(name = "length", nullable = false)
	private Integer length;

	@ManyToOne
	@JoinColumn(name = "raw_material_type_id", nullable = false)
	private RawMaterialType rawMaterialType;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "component_list_id")
	private ComponentList componentList;

	// Constructors
	public RawMaterial() {}

	public RawMaterial(Integer height, Integer width, Integer length, RawMaterialType rawMaterialType, Integer quantity) {
		this.height = height;
		this.width = width;
		this.length = length;
		this.rawMaterialType = rawMaterialType;
		this.quantity = quantity;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public RawMaterial(Integer height, Integer width, Integer length, RawMaterialType rawMaterialType, Integer quantity, ComponentList componentList) {
		this.height = height;
		this.width = width;
		this.length = length;
		this.rawMaterialType = rawMaterialType;
		this.quantity = quantity;
		this.componentList = componentList;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public RawMaterialType getRawMaterialType() {
		return rawMaterialType;
	}

	public void setRawMaterialType(RawMaterialType rawMaterialType) {
		this.rawMaterialType = rawMaterialType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ComponentList getComponentList() {
		return componentList;
	}

	public void setComponentList(ComponentList componentList) {
		this.componentList = componentList;
	}

	@Override
	public String toString() {
		return "RawMaterial [id=" + id + ", height=" + height + ", width=" + width + 
			   ", length=" + length + ", rawMaterialType=" + (rawMaterialType != null ? rawMaterialType.getName() : null) + 
			   ", quantity=" + quantity + ", componentList=" + (componentList != null ? componentList.getId() : null) + 
			   ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}