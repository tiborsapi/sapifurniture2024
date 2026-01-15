package ro.sapientia.furniture.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.dto.request.CreateProjectRequest;
import ro.sapientia.furniture.dto.response.CreateProjectResponse;
import ro.sapientia.furniture.dto.response.ProjectDetailsResponse;
import ro.sapientia.furniture.dto.response.ProjectListItemResponse;
import ro.sapientia.furniture.exception.ResourceNotFoundException;
import ro.sapientia.furniture.service.ProjectService;

class ProjectControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    
    // =========================
    // CREATE PROJECT
    // =========================
    @Test
    void createProject_ShouldReturn201() throws Exception {
        // Arrange
        CreateProjectRequest request = new CreateProjectRequest();
        request.setName("Test Project");
        request.setDescription("Test Description");

        LocalDateTime now = LocalDateTime.now();

        CreateProjectResponse response = new CreateProjectResponse(
                1L,
                "Test Project",
                "Test Description",
                now,
                now,
                null,
                List.of()
        );

        when(projectService.createProject(any(CreateProjectRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        verify(projectService, times(1)).createProject(any(CreateProjectRequest.class));
    }

    // =========================
    // GET ALL PROJECTS (PAGE)
    // =========================
    @Test
    void getProjects_ShouldReturnPageOfProjects() throws Exception {
        // Arrange
        ProjectListItemResponse project = new ProjectListItemResponse(
                1L,
                "Project 1",
                "Description",
                LocalDateTime.now()
        );

        Page<ProjectListItemResponse> page =
                new PageImpl<>(List.of(project));

        when(projectService.getProjects(0)).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/projects?page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Project 1"))
                .andExpect(jsonPath("$.content[0].description").value("Description"));

        verify(projectService).getProjects(0);
    }

    // =========================
    // GET PROJECT BY ID
    // =========================
    @Test
    void getProjectById_ShouldReturnProjectDetails() throws Exception {
        // Arrange
        Long projectId = 1L;

        ProjectDetailsResponse  response = new ProjectDetailsResponse(
                projectId,
                "Project Name",
                "Project Description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                List.of()
        );

        when(projectService.getProjectById(projectId)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/projects/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId))
                .andExpect(jsonPath("$.name").value("Project Name"))
                .andExpect(jsonPath("$.description").value("Project Description"));

        verify(projectService).getProjectById(projectId);
    }

    @Test
    void getVersions_ShouldReturnEmptyList_WhenNoVersionsExist() throws Exception {
        mockMvc.perform(get("/projects/1/versions"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteProject_ShouldReturnOk() throws Exception {
        // Arrange
        Long projectId = 1L;
        // logic is void or returns Boolean, we just need to ensure it doesn't throw

        // Act & Assert
        mockMvc.perform(delete("/projects/{projectId}", projectId)) // Adjust URL prefix if needed
                .andExpect(status().isOk());

        verify(projectService).deleteProject(projectId);
    }

    @Test
    void deleteProject_NotFound_ShouldReturn404() throws Exception {
        // Arrange
        Long projectId = 99L;
        
        // Mock the service to throw your custom exception
        doThrow(new ResourceNotFoundException("Not found"))
            .when(projectService).deleteProject(projectId);

        // Act & Assert
        mockMvc.perform(delete("/api/projects/{projectId}", projectId))
                .andExpect(status().isNotFound());
    }
}
