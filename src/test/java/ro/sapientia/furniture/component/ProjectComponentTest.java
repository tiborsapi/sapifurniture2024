package ro.sapientia.furniture.component;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.dto.request.CreateProjectRequest;
import ro.sapientia.furniture.model.Project;
import ro.sapientia.furniture.repository.ProjectRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper; // For JSON conversion (if testing POST requests)

    @BeforeEach
    public void setup() {
        // Start with a clean slate before every test to avoid interference from previous data
        projectRepository.deleteAll();
    }

    @Test
    public void getProjects_ShouldReturnSavedProject() throws Exception {
        // 1. ARRANGE: Save a project to the database
        Project project = new Project();
        project.setName("Component Test Project");
        project.setDescription("Testing the full flow");
        project.setCreatedAt(LocalDateTime.now());
        
        projectRepository.save(project);

        // 2. ACT & ASSERT (Execute and Verify)
        // Assuming your Controller listens on "/projects" and uses pagination (?page=0)
        this.mockMvc.perform(get("/projects")
                .param("page", "0")
                .param("size", "10")) 
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // Since the response is paginated (Page), the structure is usually: content: [ { ... } ]
                .andExpect(jsonPath("$.content[0].name", is("Component Test Project")))
                .andExpect(jsonPath("$.content[0].description", is("Testing the full flow")));
    }

    @Test
    public void getProjectById_ShouldReturnDetails() throws Exception {
        // 1. ARRANGE
        Project project = new Project();
        project.setName("Specific Project");
        project.setCreatedAt(LocalDateTime.now());
        var savedProject = projectRepository.save(project);

        // 2. ACT & ASSERT
        this.mockMvc.perform(get("/projects/{id}", savedProject.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Specific Project")))
                .andExpect(jsonPath("$.id", is(savedProject.getId().intValue())));
    }

    @Test
    public void createProject_ShouldSaveToDatabase() throws Exception {
        // This is a bonus test: Send a POST request and check if it gets persisted in the DB
        
        // 1. ARRANGE
        CreateProjectRequest request = new CreateProjectRequest();
        request.setName("New Created Project");
        request.setDescription("Created via MockMvc");

        // 2. ACT
        this.mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // Java Object -> JSON string
                .andExpect(status().isCreated()); // Or isOk(), depending on what your Controller returns

        // 3. ASSERT (Verify directly in the database!)
        var projects = projectRepository.findAll();
        assert(projects.size() == 1);
        assert(projects.get(0).getName().equals("New Created Project"));
    }
}