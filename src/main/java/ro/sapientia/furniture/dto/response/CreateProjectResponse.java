package ro.sapientia.furniture.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class CreateProjectResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<ProjectVersionResponse> versions;

    // Constructors
    public CreateProjectResponse() {
    }

    public CreateProjectResponse(
        Long id, 
        String name,
        String description,
        LocalDateTime createdAt, 
        LocalDateTime updatedAt, 
        LocalDateTime deletedAt,
        List<ProjectVersionResponse> versions) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.versions = versions;
    }

    public CreateProjectResponse(Long id) {
        this.id = id;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<ProjectVersionResponse> getVersions() {
        return versions;
    }

    public void setVersions(List<ProjectVersionResponse> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "CreateProjectResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", versions=" + versions +
                '}';
    }
}
