package ro.sapientia.furniture.dto.response;

import java.time.LocalDateTime;

public class UpdateProjectResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime updatedAt;

    // Constructors, Getters and Setters
    public UpdateProjectResponse(Long id, String name, String description, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() { return id;  }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}