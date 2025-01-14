package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.Window;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class WindowRepositoryTest {

    @Autowired
    private WindowRepository repository;

    @Test
    public void myTest() {
        var result = repository.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void myTestForFindFirst() {
        // Create a new Window entity
        Window window = new Window();
        window.setHeight(120);
        window.setWidth(80);
        window.setGlassType("Tempered");

        // Save the Window entity
        var savedWindow = repository.save(window);

        // Fetch all windows from the repository and check size
        var result = repository.findAll();
        assertEquals(1, result.size());

        // Find the saved window by its ID and verify it matches
        var foundObj = repository.findWindowById(savedWindow.getId());
        assertEquals(savedWindow, foundObj);
    }
}
