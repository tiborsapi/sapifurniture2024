package ro.sapientia.furniture.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateProjectRequest {

    @NotBlank(message = "Project name is required.")
    @Size(max = 255, message = "Project name must be less than 255 characters.")
    private String name;

    @Size(max = 1000, message = "Project description must be less than 1000 characters.")
    private String description;

    // ⬇️ opcionális (drawboard esetén jön)
    private List<CreateFurnitureBodyRequest> bodies;

    // -------- getters / setters --------

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

    public List<CreateFurnitureBodyRequest> getBodies() {
        return bodies;
    }

    public void setBodies(List<CreateFurnitureBodyRequest> bodies) {
        this.bodies = bodies;
    }
}
