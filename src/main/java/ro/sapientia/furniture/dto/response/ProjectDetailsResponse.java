package ro.sapientia.furniture.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import ro.sapientia.furniture.dto.response.ProjectVersionResponse;

public class ProjectDetailsResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<ProjectVersionResponse> versions;

    public ProjectDetailsResponse(
            Long id,
            String name,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            List<ProjectVersionResponse> versions
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.versions = versions;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public List<ProjectVersionResponse> getVersions() { return versions; }
}
