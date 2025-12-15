package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "component_lists")
public class ComponentList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_component_lists")
	@SequenceGenerator(name="pk_component_lists", sequenceName="pk_component_lists") 
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "componentList")
	private List<RawMaterial> rawMaterials;

	@ManyToOne
	@JoinColumn(name = "furniture_body_id")
	private FurnitureBody furnitureBody;

	// Constructors
	public ComponentList() {}

	public ComponentList(Long createdBy) {
		this.createdBy = createdBy;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public ComponentList(FurnitureBody furnitureBody, List<RawMaterial> rawMaterials) {
		this.furnitureBody = furnitureBody;
		this.rawMaterials = rawMaterials;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PrePersist
	public void prePersist() {
		if (createdAt == null) createdAt = LocalDateTime.now();
		if (updatedAt == null) updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public List<RawMaterial> getRawMaterials() {
		return rawMaterials;
	}

	public void setRawMaterials(List<RawMaterial> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}

	public FurnitureBody getFurnitureBody() {
		return furnitureBody;
	}

	public void setFurnitureBody(FurnitureBody furnitureBody) {
		this.furnitureBody = furnitureBody;
	}

	@Override
	public String toString() {
		return "ComponentList [id=" + id + ", createdBy=" + createdBy + 
			   ", furnitureBody=" + (furnitureBody != null ? furnitureBody.getId() : "null") + ", createdAt=" + createdAt + 
			   ", updatedAt=" + updatedAt + "]";
	}
}