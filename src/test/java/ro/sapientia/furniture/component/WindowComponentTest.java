package ro.sapientia.furniture.component;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.model.Window;
import ro.sapientia.furniture.repository.WindowRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WindowComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WindowRepository repository;

    @Test
    public void shouldReturnWindowDetails() throws Exception {
        // Create and save a new Window entity
        Window window = new Window();
        window.setHeight(120);
        window.setWidth(80);
        window.setGlassType("Tempered");
        var savedWindow = repository.save(window);

        // Perform GET request to fetch all windows
        this.mockMvc.perform(get("/window/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(120)))
                .andExpect(jsonPath("$[0].glassType", is("Tempered")));
    }
}
