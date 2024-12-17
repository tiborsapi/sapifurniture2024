
package ro.sapientia.furniture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "furniture_stopper")
public class FurnitureStopper implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_furniture_stopper")
    @SequenceGenerator(name = "pk_furniture_stopper", sequenceName = "pk_furniture_stopper")
    @Column(name = "id", nullable = false, updatable = false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "furniture_body_id", nullable = true)
    @JsonProperty("furniture_body_id")
    private FurnitureBody furnitureBody;

    @Column(name = "active", nullable = false)
    @JsonProperty("active")
    private boolean active;

    @Column(name = "stop_time_ms", nullable = false)
    @JsonProperty("stop_time_ms")
    private long stopTimeMs;

    @Column(name = "start_time_ms", nullable = false)
    @JsonProperty("start_time_ms")
    private long startTimeMs;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getStopTimeMs() {
        return stopTimeMs;
    }

    public void setStopTimeMs(long stopTimeMs) {
        this.stopTimeMs = stopTimeMs;
    }

    public long getStartTimeMs() {
        return startTimeMs;
    }

    public void setStartTimeMs(long startTimeMs) {
        this.startTimeMs = startTimeMs;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean canChangeState() {
        // The furniture stopper can change state if the last action was performed
        // more than "stopTimeMs" milliseconds ago.
        return startTimeMs + stopTimeMs <= System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "FurnitureStopper{" +
                "id=" + id +
                ", furnitureBodyId=" + (furnitureBody != null ? furnitureBody.getId() : null) +
                ", active=" + active +
                ", stopTimeMs=" + stopTimeMs +
                ", startTimeMs=" + startTimeMs +
                '}';
    }
}
