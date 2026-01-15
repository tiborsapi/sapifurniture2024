package ro.sapientia.furniture.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UpdateProjectRequest {
    @NotBlank
    private String name;
    private String description;
    private List<CreateFurnitureBodyRequest> bodies;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<CreateFurnitureBodyRequest> getBodies() { return bodies; }
    public void setBodies(List<CreateFurnitureBodyRequest> bodies) { this.bodies = bodies; }
    
}