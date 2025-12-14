package ro.sapientia.furniture.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity(name = "manufactured_component_types")
@Table(name = "manufactured_component_types", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})
})
public class ManufacturedComponentType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pk_manufactured_component_types")
    @SequenceGenerator(name="pk_manufactured_component_types", sequenceName="pk_manufactured_component_types") 
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Constructors
    public ManufacturedComponentType() {}

    public ManufacturedComponentType(String name) {
        this.name = name;
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

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "ManufacturedComponentType [id=" + id + 
            ", name=" + name + 
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt + "]";
    }
}