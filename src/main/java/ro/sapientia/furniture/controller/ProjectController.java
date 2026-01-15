package ro.sapientia.furniture.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.sapientia.furniture.dto.request.CreateProjectRequest;
import ro.sapientia.furniture.dto.request.UpdateProjectRequest;
import ro.sapientia.furniture.dto.response.CreateProjectResponse;
import ro.sapientia.furniture.dto.response.ProjectDetailsResponse;
import ro.sapientia.furniture.dto.response.ProjectListItemResponse;
import ro.sapientia.furniture.dto.response.ProjectVersionResponse;
import ro.sapientia.furniture.dto.response.UpdateProjectResponse;
import ro.sapientia.furniture.model.Project;
import ro.sapientia.furniture.service.ProjectService;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Page<ProjectListItemResponse>> getProjects(
            @RequestParam(defaultValue = "0") int page) {

        return ResponseEntity.ok(
                projectService.getProjects(page)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailsResponse> getProjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                projectService.getProjectById(id)
        );
    }


    @PostMapping
    public ResponseEntity<CreateProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request) {

        CreateProjectResponse response = projectService.createProject(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request) {

        UpdateProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<ProjectVersionResponse>> getProjectVersions(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectVersions(id));
    }

    @PostMapping("/{id}/versions/{versionId}/restore")
    public ResponseEntity<UpdateProjectResponse> restoreVersion(
    @PathVariable Long id,
    @PathVariable Long versionId) {
    
        Project restoredProject = projectService.restoreVersion(id, versionId);
        
        return ResponseEntity.ok(new UpdateProjectResponse(
                restoredProject.getId(),
                restoredProject.getName(),
                restoredProject.getDescription(),
                restoredProject.getUpdatedAt()
        ));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId) {

        projectService.deleteProject(projectId);
        return ResponseEntity.ok().build();
    }
}
