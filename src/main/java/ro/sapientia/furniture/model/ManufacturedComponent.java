package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity(name = "manufactured_components")
@Table(name = "manufactured_components")
public class ManufacturedComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_manufactured_components")
    @SequenceGenerator(name="pk_manufactured_components", sequenceName="pk_manufactured_components") 
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "component_list_id", nullable = false)
    private ComponentList componentList;

    @ManyToOne
    @JoinColumn(name = "manufactured_component_type_id", nullable = false)
    private ManufacturedComponentType manufacturedComponentType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public ManufacturedComponent() {}

    public ManufacturedComponent(ComponentList componentList, ManufacturedComponentType manufacturedComponentType, Integer quantity) {
        this.componentList = componentList;
        this.manufacturedComponentType = manufacturedComponentType;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ComponentList getComponentList() { return componentList; }
    public void setComponentList(ComponentList componentList) { this.componentList = componentList; }

    public ManufacturedComponentType getManufacturedComponentType() { return manufacturedComponentType; }
    public void setManufacturedComponentType(ManufacturedComponentType manufacturedComponentType) { this.manufacturedComponentType = manufacturedComponentType; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "ManufacturedComponent [id=" + id +
            ", componentList=" + (componentList != null ? componentList.getId() : null) + 
            ", manufacturedComponentType=" + (manufacturedComponentType != null ? manufacturedComponentType.getName() : null) +
            ", quantity=" + quantity +
            ", createdAt=" + createdAt + 
            ", updatedAt=" + updatedAt + "]";
    }
}