package ro.sapientia.furniture.component;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import ro.sapientia.furniture.model.Cupboard;
import ro.sapientia.furniture.repository.CupboardRepository;
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CupboardControllerTest {
    @Autowired MockMvc mockMvc;

    @Autowired
    CupboardRepository cupboardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        cupboardRepository.deleteAll();
    }

    @Test
    public void testCreateCupboard() throws Exception {
        Cupboard cupboard = new Cupboard();
        cupboard.setHeight(500);
        cupboard.setWidth(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);

        mockMvc.perform(post("/cupboard/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cupboard)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.height", is(500)))
                .andExpect(jsonPath("$.width", is(500)))
                .andExpect(jsonPath("$.numberOfCab", is(5)))
                .andExpect(jsonPath("$.numberOfDrawer", is(5)));
    }

    @Test
    public void testGetAllCupboards() throws Exception {
        Cupboard cupboard = new Cupboard();
        cupboard.setHeight(500);
        cupboard.setWidth(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);
        cupboardRepository.save(cupboard);

        mockMvc.perform(get("/cupboard/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(500)))
                .andExpect(jsonPath("$[0].width", is(500)))
                .andExpect(jsonPath("$[0].numberOfCab", is(5)))
                .andExpect(jsonPath("$[0].numberOfDrawer", is(5)));
    }

    @Test
    public void testGetCupboardById() throws Exception {
        Cupboard cupboard = new Cupboard();
        cupboard.setHeight(500);
        cupboard.setWidth(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);
        Cupboard savedCupboard = cupboardRepository.save(cupboard);

        mockMvc.perform(get("/cupboard/find/{id}", savedCupboard.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height", is(500)))
                .andExpect(jsonPath("$.width", is(500)));
    }

    @Test
    public void testUpdateCupboard() throws Exception {
        Cupboard cupboard = new Cupboard();
        cupboard.setHeight(500);
        cupboard.setWidth(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);
        Cupboard savedCupboard = cupboardRepository.save(cupboard);

        savedCupboard.setHeight(600);
        savedCupboard.setWidth(600);

        mockMvc.perform(put("/cupboard/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedCupboard)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height", is(600)))
                .andExpect(jsonPath("$.width", is(600)))
                .andExpect(jsonPath("$.numberOfCab", is(5)))
                .andExpect(jsonPath("$.numberOfDrawer", is(5)));
    }

    @Test
    public void testDeleteCupboard() throws Exception {
        Cupboard cupboard = new Cupboard();
        cupboard.setHeight(500);
        cupboard.setWidth(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);
        Cupboard savedCupboard = cupboardRepository.save(cupboard);

        mockMvc.perform(delete("/cupboard/delete/{id}", savedCupboard.getId()))
                .andExpect(status().isOk());

        assertFalse(cupboardRepository.findById(savedCupboard.getId()).isPresent());
    }

    @Test
    public void testInvalidCupboardData() throws Exception {
        Cupboard cupboard = new Cupboard();
        cupboard.setHeight(-500);
        cupboard.setWidth(-500);
        cupboard.setNumberOfCab(0);
        cupboard.setNumberOfDrawer(0);

        mockMvc.perform(post("/cupboard/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cupboard)))
                .andExpect(status().isBadRequest());
    }
}
