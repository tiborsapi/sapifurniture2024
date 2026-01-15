package ro.sapientia.furniture.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.dto.request.CreateProjectRequest;
import ro.sapientia.furniture.dto.request.UpdateProjectRequest;
import ro.sapientia.furniture.dto.request.CreateFurnitureBodyRequest;
import ro.sapientia.furniture.dto.response.CreateProjectResponse;
import ro.sapientia.furniture.dto.response.FurnitureBodyResponse;
import ro.sapientia.furniture.dto.response.ProjectDetailsResponse;
import ro.sapientia.furniture.dto.response.ProjectListItemResponse;
import ro.sapientia.furniture.dto.response.ProjectVersionResponse;
import ro.sapientia.furniture.exception.EntityNotFoundException;
import ro.sapientia.furniture.dto.response.UpdateProjectResponse;
import ro.sapientia.furniture.exception.ServiceUnavailableException;
import ro.sapientia.furniture.exception.ResourceNotFoundException;
import ro.sapientia.furniture.model.FurnitureBody;

import ro.sapientia.furniture.model.Project;
import ro.sapientia.furniture.model.ProjectVersion;
import ro.sapientia.furniture.repository.ProjectRepository;
import ro.sapientia.furniture.repository.ProjectVersionRepository;

@Service
public class ProjectService {

    private static final int PAGE_SIZE = 10;

    private final ProjectRepository projectRepository;
    private final ProjectVersionRepository projectVersionRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectVersionRepository projectVersionRepository) {
        this.projectRepository = projectRepository;
        this.projectVersionRepository = projectVersionRepository;
    }

    // --------------------
    // CREATE PROJECT
    // --------------------
    @Transactional
        public CreateProjectResponse createProject(CreateProjectRequest request) {
        LocalDateTime now = LocalDateTime.now();

        // 1. Project
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setCreatedAt(now);
        project.setUpdatedAt(now);
        project.setDeletedAt(null);

        // 2. Initial version
        ProjectVersion version = new ProjectVersion();
        version.setVersionNumber(1);
        version.setSavedAt(now);
        version.setVersionNote("Initial version");
        version.setProject(project);
        version.setName(project.getName());        // <= fontos
        version.setDescription(project.getDescription()); // ha van

        project.getVersions().add(version);

        // 3. OPTIONAL Furniture bodies
        if (request.getBodies() != null && !request.getBodies().isEmpty()) {
                request.getBodies().forEach(bodyReq -> {
                FurnitureBody body = new FurnitureBody();
                body.setWidth(bodyReq.getWidth());
                body.setHeigth(bodyReq.getHeigth());
                body.setDepth(bodyReq.getDepth());
                body.setVersion(version);

                version.getBodies().add(body);
                });
        }

        Project savedProject = projectRepository.save(project);

        // 4. Response mapping
        List<ProjectVersionResponse> versionResponses =
        savedProject.getVersions().stream()
                .map(v -> new ProjectVersionResponse(
                        v.getId(),
                        v.getVersionNumber(),
                        v.getSavedAt(),
                        v.getVersionNote(),
                        v.getBodies().stream()
                                .map(b -> new FurnitureBodyResponse(
                                        b.getId(),
                                        b.getWidth(),
                                        b.getHeigth(),
                                        b.getDepth()
                                ))
                                .toList(),
                        v.getName(),
                        v.getDescription()
                ))
                .toList();

        return new CreateProjectResponse(
                savedProject.getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                savedProject.getCreatedAt(),
                savedProject.getUpdatedAt(),
                savedProject.getDeletedAt(),
                versionResponses
        );
    }

    // --------------------
    // GET PROJECTS (paged)
    // --------------------
    public Page<ProjectListItemResponse> getProjects(int page) {
        try {
            Pageable pageable = PageRequest.of(
                    page,
                    PAGE_SIZE,
                    Sort.by("createdAt").descending()
            );

            Page<Project> projects = projectRepository.findByDeletedAtIsNull(pageable);

            return projects.map(p -> new ProjectListItemResponse(
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getCreatedAt()
            ));

        } catch (DataAccessException ex) {
            throw new ServiceUnavailableException("Database error");
        }
    }

    // --------------------
    // GET PROJECT BY ID
    // --------------------

   @Transactional(readOnly = true)
    public ProjectDetailsResponse getProjectById(Long id) {

        Project project = projectRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Project not found with id: " + id)
                );

        List<ProjectVersionResponse> versionResponses =
                project.getVersions().stream()
                        .map(v -> new ProjectVersionResponse(
                                v.getId(),
                                v.getVersionNumber(),
                                v.getSavedAt(),
                                v.getVersionNote(),
                                v.getBodies().stream()
                                        .map(b -> new FurnitureBodyResponse(
                                                b.getId(),
                                                b.getWidth(),
                                                b.getHeigth(),
                                                b.getDepth()
                                        ))
                                        .toList(),
                                v.getName(),
                                v.getDescription()
                        ))
                        .toList();

        return new ProjectDetailsResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getDeletedAt(),
                versionResponses
        );
    }

    // --------------------
    // UPDATE PROJECT
    // --------------------
    @Transactional
public UpdateProjectResponse updateProject(Long id, UpdateProjectRequest request) {
    Project project = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));

    // 1. Create new version WITH bodies
    createSnapshotWithBodies(project, request.getName(), request.getDescription(), request.getBodies());

    // 2. Update the actual project
    project.setName(request.getName());
    project.setDescription(request.getDescription());
    project.setUpdatedAt(LocalDateTime.now());

    Project updatedProject = projectRepository.save(project);

    return new UpdateProjectResponse(
            updatedProject.getId(),
            updatedProject.getName(),
            updatedProject.getDescription(),
            updatedProject.getUpdatedAt()
    );
}

private void createSnapshotWithBodies(Project project, String name, String description, List<CreateFurnitureBodyRequest> bodies) {
    List<ProjectVersion> versions = projectVersionRepository
            .findByProjectIdOrderByVersionNumberAsc(project.getId());

    // Keep only the last 10: Delete the oldest if we're at the limit
    if (versions.size() >= 10) {
        ProjectVersion oldestVersion = versions.get(0);
        // FONTOS: Távolítsd el a project versions kollekcióból is!
        project.getVersions().remove(oldestVersion);
        projectVersionRepository.delete(oldestVersion);
        projectVersionRepository.flush();  // Biztosítja, hogy a törlés megtörténjen
    }

    // Determine next version number
    int nextVersionNumber = versions.isEmpty() ? 1 : 
            versions.get(versions.size() - 1).getVersionNumber() + 1;

    // Create new version entry
    ProjectVersion newVersion = new ProjectVersion();
    newVersion.setProject(project);
    newVersion.setVersionNumber(nextVersionNumber);
    newVersion.setSavedAt(LocalDateTime.now());
    newVersion.setVersionNote("Manual update to: " + name);
    newVersion.setName(name);
    newVersion.setDescription(description);

    // Bodies logic: use provided bodies OR copy from latest version
    if (bodies != null && !bodies.isEmpty()) {
        bodies.forEach(bodyReq -> {
            FurnitureBody body = new FurnitureBody();
            body.setWidth(bodyReq.getWidth());
            body.setHeigth(bodyReq.getHeigth());
            body.setDepth(bodyReq.getDepth());
            body.setVersion(newVersion);
            newVersion.getBodies().add(body);
        });
    } else if (!versions.isEmpty()) {
        // Copy bodies from the latest version (but skip if it was the deleted one)
        ProjectVersion latestVersion = versions.get(versions.size() - 1);
        latestVersion.getBodies().forEach(existingBody -> {
            FurnitureBody bodyCopy = new FurnitureBody();
            bodyCopy.setWidth(existingBody.getWidth());
            bodyCopy.setHeigth(existingBody.getHeigth());
            bodyCopy.setDepth(existingBody.getDepth());
            bodyCopy.setVersion(newVersion);
            newVersion.getBodies().add(bodyCopy);
        });
    }

    // FONTOS: Add hozzá a project versions kollekcióhoz!
    project.getVersions().add(newVersion);
    
    projectVersionRepository.save(newVersion);
}
    public List<ProjectVersionResponse> getProjectVersions(Long projectId) {
        // Verify project exists first
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found");
        }

        // Fetch versions using the repository method we'll define below
        return projectVersionRepository.findByProjectIdOrderByVersionNumberDesc(projectId)
                .stream()
                .map(v -> new ProjectVersionResponse(
                        v.getId(),
                        v.getVersionNumber(),
                        v.getSavedAt(),
                        v.getVersionNote(),
                        v.getBodies().stream()
                                .map(b -> new FurnitureBodyResponse(
                                        b.getId(),
                                        b.getWidth(),
                                        b.getHeigth(),
                                        b.getDepth()
                                ))
                                .collect(Collectors.toList()),
                        v.getName(),
                        v.getDescription()
                ))
                .collect(Collectors.toList());
    }


        @Transactional
        public Project restoreVersion(Long projectId, Long versionId) {
        Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        ProjectVersion version = projectVersionRepository.findById(versionId)
        .orElseThrow(() -> new ResourceNotFoundException("Version not found"));

        // Force initialize the bodies collection (Lazy loading fix)
        version.getBodies().size();

        // 1. Convert the restored version's bodies to request format
        List<CreateFurnitureBodyRequest> restoredBodies = version.getBodies().stream()
        .map(body -> {
        CreateFurnitureBodyRequest req = new CreateFurnitureBodyRequest();
        req.setWidth(body.getWidth());
        req.setHeigth(body.getHeigth());
        req.setDepth(body.getDepth());
        return req;
        })
        .collect(Collectors.toList());

        // 2. Create snapshot WITH the restored version's bodies
        createSnapshotWithBodies(project, version.getName(), version.getDescription(), restoredBodies);

        // 3. Overwrite project fields with version data
        project.setName(version.getName());
        project.setDescription(version.getDescription());
        project.setUpdatedAt(LocalDateTime.now());

        return projectRepository.save(project);
        }

    // --------------------
    // DELETE PROJECT
    // --------------------
    @Transactional
    public Boolean deleteProject(Long projectId) {
        try {
            Project project = projectRepository.findById(projectId).orElseThrow(() -> { return new ResourceNotFoundException("Project not found"); });

            project.setDeletedAt(LocalDateTime.now());
            projectRepository.save(project);

            return true;
        } catch (DataAccessException ex) {
            throw new ServiceUnavailableException("Database error");
        }
    }
}
