package ro.sapientia.furniture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.Project;
import ro.sapientia.furniture.model.ProjectVersion;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ProjectVersionRepositoryTest {

    @Autowired
    ProjectVersionRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testFindByProjectIdOrderByVersionNumberDesc() {
        // 1. Create Parent Project
        Project project = new Project();
        project.setName("Test Project Desc");
        project.setDescription("Parent description"); // Added for safety
        project.setCreatedAt(LocalDateTime.now());
        var savedProject = projectRepository.save(project);

        // 2. Create Versions (Now setting NAME to satisfy DB constraint)
        ProjectVersion v1 = new ProjectVersion();
        v1.setProject(savedProject);
        v1.setVersionNumber(1);
        v1.setName("Version 1 Name");  // <--- FIXED: Added Name
        v1.setSavedAt(LocalDateTime.now());
        repository.save(v1);

        ProjectVersion v2 = new ProjectVersion();
        v2.setProject(savedProject);
        v2.setVersionNumber(2);
        v2.setName("Version 2 Name");  // <--- FIXED: Added Name
        v2.setSavedAt(LocalDateTime.now());
        repository.save(v2);

        // 3. Test
        var result = repository.findByProjectIdOrderByVersionNumberDesc(savedProject.getId());

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getVersionNumber()); 
        assertEquals(1, result.get(1).getVersionNumber());
    }

    @Test
    public void testFindByProjectIdOrderByVersionNumberAsc() {
        // 1. Create Parent Project
        Project project = new Project();
        project.setName("Test Project Asc");
        project.setCreatedAt(LocalDateTime.now());
        var savedProject = projectRepository.save(project);

        // 2. Create Versions
        ProjectVersion v2 = new ProjectVersion();
        v2.setProject(savedProject);
        v2.setVersionNumber(2);
        v2.setName("Version 2 Name"); // <--- FIXED
        repository.save(v2);

        ProjectVersion v1 = new ProjectVersion();
        v1.setProject(savedProject);
        v1.setVersionNumber(1);
        v1.setName("Version 1 Name"); // <--- FIXED
        repository.save(v1);

        // 3. Test
        var result = repository.findByProjectIdOrderByVersionNumberAsc(savedProject.getId());

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getVersionNumber());
        assertEquals(2, result.get(1).getVersionNumber());
    }

    @Test
    public void testFindProjectVersionById() {
        // 1. Setup
        Project project = new Project();
        project.setName("Test Project Single");
        var savedProject = projectRepository.save(project);

        ProjectVersion pv = new ProjectVersion();
        pv.setProject(savedProject);
        pv.setVersionNumber(10);
        pv.setName("Version 10 Name"); // <--- FIXED
        var savedVersion = repository.save(pv);

        // 2. Find
        var foundVersion = repository.findProjectVersionById(savedVersion.getId());

        // 3. Assert
        assertEquals(savedVersion.getId(), foundVersion.getId());
        assertEquals(10, foundVersion.getVersionNumber());
    }
}