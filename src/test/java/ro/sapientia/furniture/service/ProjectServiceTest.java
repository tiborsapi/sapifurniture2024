package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ro.sapientia.furniture.dto.request.CreateProjectRequest;
import ro.sapientia.furniture.dto.request.UpdateProjectRequest;
import ro.sapientia.furniture.dto.response.UpdateProjectResponse;
import ro.sapientia.furniture.dto.response.CreateProjectResponse;
import ro.sapientia.furniture.dto.response.ProjectDetailsResponse;
import ro.sapientia.furniture.dto.response.ProjectListItemResponse;
import ro.sapientia.furniture.dto.response.ProjectVersionResponse;
import ro.sapientia.furniture.exception.EntityNotFoundException;
import ro.sapientia.furniture.exception.ResourceNotFoundException;
import ro.sapientia.furniture.exception.ServiceUnavailableException;
import ro.sapientia.furniture.model.Project;
import ro.sapientia.furniture.model.ProjectVersion;
import ro.sapientia.furniture.repository.ProjectRepository;
import ro.sapientia.furniture.repository.ProjectVersionRepository;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectVersionRepository projectVersionRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =========================
    // GET ALL PROJECTS
    // =========================
    @Test
    @DisplayName("Get Projects - Success")
    void getProjects_ShouldReturnPagedProjects() {
        // Arrange
        Project project = new Project();
        project.setId(1L);
        project.setName("Project 1");
        project.setDescription("Description");
        project.setCreatedAt(LocalDateTime.now());

        Pageable pageable;
        pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Project> page;
        page = new PageImpl<>(List.of(project), pageable, 1);

        when(projectRepository.findByDeletedAtIsNull(any(Pageable.class)))
                .thenReturn(page);

        // Act
        Page<ProjectListItemResponse> result;
        result = projectService.getProjects(0);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Project 1", result.getContent().get(0).getName());

        verify(projectRepository).findByDeletedAtIsNull(any(Pageable.class));
    }

    @Test
    @DisplayName("Get Projects - Database error")
    void getProjects_DatabaseError_ShouldThrowServiceUnavailable() {
        when(projectRepository.findByDeletedAtIsNull(any(Pageable.class)))
                .thenThrow(new DataAccessException("DB error") {});

        assertThrows(ServiceUnavailableException.class, () -> {
            projectService.getProjects(0);
        });
    }

    // =========================
    // GET PROJECT BY ID
    // =========================
    @Test
    @DisplayName("Get Project By ID - Success")
    void getProjectById_ShouldReturnDetails() {
        // Arrange
        Long projectId = 1L;

        Project project = new Project();
        project.setId(projectId);
        project.setName("Project Name");
        project.setDescription("Project Description");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());

        ProjectVersion version = new ProjectVersion();
        version.setId(100L);
        version.setVersionNumber(1);
        version.setProject(project);
        project.getVersions().add(version);

        when(projectRepository.findByIdAndDeletedAtIsNull(projectId))
                .thenReturn(Optional.of(project));

        // Act
        ProjectDetailsResponse response = projectService.getProjectById(projectId);

        // Assert
        assertNotNull(response);
        assertEquals(projectId, response.getId());
        assertEquals("Project Name", response.getName());
        assertEquals(1, response.getVersions().size());

        verify(projectRepository).findByIdAndDeletedAtIsNull(projectId);
    }

    @Test
    @DisplayName("Get Project By ID - Not found")
    void getProjectById_NotFound_ShouldThrowException() {
        when(projectRepository.findByIdAndDeletedAtIsNull(99L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            projectService.getProjectById(99L);
        });
    }


    @Test
    void testCreateProject() {
        String projectName = "Test Project";
        String projectDescription = "This is a test project.";
        LocalDateTime now = LocalDateTime.now();

        Project project = new Project();
        project.setId(1L);
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setCreatedAt(now);
        project.setUpdatedAt(now);

        ProjectVersion version = new ProjectVersion();
        version.setId(1L);
        version.setVersionNumber(1);
        version.setSavedAt(now);
        version.setVersionNote("Initial version");
        version.setProject(project);
        project.getVersions().add(version);

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        CreateProjectRequest request = new CreateProjectRequest();
        request.setName(projectName);
        request.setDescription(projectDescription);
        // ha van bodies listája, azt is itt lehet beállítani
        // request.setBodies(...);

        CreateProjectResponse result = projectService.createProject(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(projectName, result.getName());
        assertEquals(projectDescription, result.getDescription());
        assertNotNull(result.getCreatedAt());
        assertEquals(1, result.getVersions().size());
        assertEquals(1, result.getVersions().get(0).getVersionNumber());

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    @DisplayName("Update Project - Success and Version Snapshot Created")
    void updateProject_Success() {
        // Arrange
        Long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setName("Old Name");
        existingProject.setDescription("Old Description");

        UpdateProjectRequest request = new UpdateProjectRequest();
        request.setName("New Name");
        request.setDescription("New Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectVersionRepository.findByProjectIdOrderByVersionNumberAsc(projectId)).thenReturn(new ArrayList<>());
        when(projectRepository.save(any(Project.class))).thenReturn(existingProject);

        // Act
        UpdateProjectResponse result = projectService.updateProject(projectId, request);

        // Assert
        assertEquals("New Name", result.getName());
        assertNotNull(result.getUpdatedAt());
        verify(projectVersionRepository, times(1)).save(any(ProjectVersion.class));
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    @DisplayName("Update Project - Delete oldest version when limit of 10 is reached")
    void updateProject_MaintainMax10Versions() {
        // Arrange
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        List<ProjectVersion> tenVersions = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ProjectVersion v = new ProjectVersion();
            v.setVersionNumber(i);
            tenVersions.add(v);
        }

        UpdateProjectRequest request = new UpdateProjectRequest();
        request.setName("New Name");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectVersionRepository.findByProjectIdOrderByVersionNumberAsc(projectId)).thenReturn(tenVersions);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        projectService.updateProject(projectId, request);

        // Assert: Verify the first (oldest) version was deleted
        verify(projectVersionRepository, times(1)).delete(tenVersions.get(0));
        // Verify the 11th version was saved
        verify(projectVersionRepository, times(1)).save(argThat(v -> v.getVersionNumber() == 11));
    }

    @Test
    @DisplayName("Get Project Versions - Return mapped DTOs")
    void getProjectVersions_Success() {
        // Arrange
        Long projectId = 1L;
        ProjectVersion v1 = new ProjectVersion();
        v1.setVersionNumber(1);
        
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(projectVersionRepository.findByProjectIdOrderByVersionNumberDesc(projectId))
            .thenReturn(List.of(v1));

        // Act
        List<ProjectVersionResponse> result = projectService.getProjectVersions(projectId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.get(0).getVersionNumber());
    }

    @Test
    @DisplayName("Restore Version - Should update project fields with version data")
    void restoreVersion_Success() {
        // GIVEN
        Long projectId = 1L;
        Long versionId = 100L;
        
        Project currentProject = new Project();
        currentProject.setId(projectId);
        currentProject.setName("Current Name");

        ProjectVersion versionToRestore = new ProjectVersion();
        versionToRestore.setId(versionId);
        versionToRestore.setName("Old Historic Name");
        versionToRestore.setDescription("Old Description");
        versionToRestore.setProject(currentProject);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(currentProject));
        when(projectVersionRepository.findById(versionId)).thenReturn(Optional.of(versionToRestore));
        when(projectRepository.save(any(Project.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Project result = projectService.restoreVersion(projectId, versionId);

        // THEN
        assertEquals("Old Historic Name", result.getName());
        assertEquals("Old Description", result.getDescription());
        // Verify a snapshot was taken BEFORE restoring (optional but good for your logic)
        verify(projectVersionRepository, atLeastOnce()).save(any(ProjectVersion.class));
    }

    @Test
    void deleteProject_Success_ShouldSoftDelete() {
        // Arrange
        Long projectId = 1L;
        Project mockProject = new Project();
        mockProject.setId(projectId);
        mockProject.setDeletedAt(null); // Initially active

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockProject));

        // Act
        Boolean result = projectService.deleteProject(projectId);

        // Assert
        assertTrue(result);
        assertNotNull(mockProject.getDeletedAt(), "deletedAt should be set to current timestamp");
        verify(projectRepository).save(mockProject); // Verify save was called
    }

    @Test
    void deleteProject_NotFound_ShouldThrowException() {
        // Arrange
        Long projectId = 99L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            projectService.deleteProject(projectId);
        });

        verify(projectRepository, never()).save(any());
    }

    @Test
    void deleteProject_DatabaseError_ShouldThrowServiceUnavailable() {
        // Arrange
        Long projectId = 1L;
        when(projectRepository.findById(projectId))
            .thenThrow(new DataIntegrityViolationException("DB Error")); // Simulating DataAccessException

        // Act & Assert
        assertThrows(ServiceUnavailableException.class, () -> {
            projectService.deleteProject(projectId);
        });
    }
}

// Run the tests with: ./mvnw test -Dtest=ProjectServiceTest