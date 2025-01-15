package ro.sapientia.furniture.component;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.RockingChairModel;
import ro.sapientia.furniture.repository.RockingChairRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RockingChairComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RockingChairRepository rockingChairRepository;

    @Test
    public void testGetAllRockingChairs() throws Exception {
        // Create some rocking chairs
        RockingChairModel chair1 = new RockingChairModel();
        chair1.setMaterial("Wood");
        rockingChairRepository.save(chair1);

        RockingChairModel chair2 = new RockingChairModel();
        chair2.setMaterial("Metal");
        rockingChairRepository.save(chair2);

        mockMvc.perform(get("/rocking-chairs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].material", is("Wood")))
                .andExpect(jsonPath("$[1].material", is("Metal")));
    }

    @Test
    public void testGetRockingChairById_existingId() throws Exception {
        // Create a rocking chair
        RockingChairModel chair = new RockingChairModel();
        chair.setMaterial("Wood");
        rockingChairRepository.save(chair);
        Long chairId = chair.getId();

        // Perform the GET request
        mockMvc.perform(get("/rocking-chairs/" + chairId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRockingChairById_nonExistingId() throws Exception {
        // Perform the GET request with a non-existing ID
        mockMvc.perform(get("/rocking-chairs/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetRockingChairsByMaterial_existingMaterial() throws Exception {
        RockingChairModel chair1 = new RockingChairModel();
        chair1.setMaterial("Wood");
        rockingChairRepository.save(chair1);

        RockingChairModel chair2 = new RockingChairModel();
        chair2.setMaterial("Metal");
        rockingChairRepository.save(chair2);

        RockingChairModel chair3 = new RockingChairModel();
        chair3.setMaterial("Wood");
        rockingChairRepository.save(chair3);

        mockMvc.perform(get("/rocking-chairs/material/Wood"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].material", is("Wood")));
    }

    @Test
    public void testGetRockingChairsByMaterial_nonExistingMaterial() throws Exception {
        RockingChairModel chair1 = new RockingChairModel();
        chair1.setMaterial("Wood");
        rockingChairRepository.save(chair1);

        RockingChairModel chair2 = new RockingChairModel();
        chair2.setMaterial("Metal");
        rockingChairRepository.save(chair2);

        mockMvc.perform(get("/rocking-chairs/material/plastic"))
                .andExpect(status().isNotFound());
    }
}
