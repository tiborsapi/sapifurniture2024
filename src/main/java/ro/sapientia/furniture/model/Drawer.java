package ro.sapientia.furniture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "drawer",
        indexes = {
                @Index(name = "idx_drawer_material", columnList = "material")
        },
        schema = "public"
)
public class Drawer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Long id;

    @NotNull
    @Column(nullable = false, length = 50)
    private String material;

    @Column(nullable = false, length = 30)
    private String color;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private Double height;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private Double width;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private Double depth;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private Double weight;

    @Column(name = "is_open", nullable = false, columnDefinition = "boolean default false")
    private boolean isOpen;

    @NotNull
    @Positive
    @Column(name = "max_open_distance", nullable = false, precision = 10, scale = 2)
    private Double maxOpenDistance;

    @NotNull
    @Column(name = "current_open_distance", nullable = false, precision = 10, scale = 2)
    private Double currentOpenDistance;

    @NotNull
    @Positive
    @Column(name = "weight_capacity", nullable = false, precision = 10, scale = 2)
    private Double weightCapacity;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DrawerStatus status;

    public enum DrawerStatus {
        FUNCTIONAL, DAMAGED, UNDER_REPAIR, DECOMMISSIONED
    }

    public void open() {
        if (!isOpen && status == DrawerStatus.FUNCTIONAL) {
            this.isOpen = true;
            this.currentOpenDistance = maxOpenDistance;
        }
    }

    public void close() {
        if (isOpen && status == DrawerStatus.FUNCTIONAL) {
            this.isOpen = false;
            this.currentOpenDistance = 0.0;
        }
    }

    public void slidePartially(Double distance) {
        if (status == DrawerStatus.FUNCTIONAL &&
                distance >= 0 &&
                distance <= maxOpenDistance) {
            this.currentOpenDistance = distance;
            this.isOpen = distance > 0;
        }
    }

    @PrePersist
    public void prePersist() {
        validateState();
        if (status == null) {
            status = DrawerStatus.FUNCTIONAL;
        }
    }

    @PreUpdate
    private void preUpdate() {
        validateState();
    }

    private void validateState() {
        if (currentOpenDistance > maxOpenDistance) {
            throw new IllegalStateException("Current open distance cannot be greater than max open distance");
        }
        if (weight > weightCapacity) {
            throw new IllegalStateException("Drawer weight cannot exceed weight capacity");
        }
        if (height <= 0 || width <= 0 || depth <= 0) {
            throw new IllegalStateException("Dimensions must be positive values");
        }
    }
}