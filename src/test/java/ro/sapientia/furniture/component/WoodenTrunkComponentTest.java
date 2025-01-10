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

import ro.sapientia.furniture.model.WoodenTrunk;
import ro.sapientia.furniture.repository.WoodenTrunkRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WoodenTrunkComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WoodenTrunkRepository repository;

    @Test
    public void testGetAllWoodenTrunks() throws Exception {
        // Given: Create and save a WoodenTrunk instance
        WoodenTrunk trunk = new WoodenTrunk();
        trunk.setHeight(100);
        trunk.setWidth(50);
        trunk.setDepth(40);
        trunk.setMaterial("oak");
        trunk.setHasLid(true);
        trunk.setCapacity(200);
        var savedTrunk = repository.save(trunk);

        // When & Then: Perform a GET request and validate the response
        this.mockMvc.perform(get("/wooden-trunk/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(100)))
                .andExpect(jsonPath("$[0].width", is(50)))
                .andExpect(jsonPath("$[0].depth", is(40)))
                .andExpect(jsonPath("$[0].material", is("oak")))
                .andExpect(jsonPath("$[0].hasLid", is(true)))
                .andExpect(jsonPath("$[0].capacity", is(200)));
    }
}
