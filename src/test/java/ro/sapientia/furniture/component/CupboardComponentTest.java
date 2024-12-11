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
import ro.sapientia.furniture.model.Cupboard;
import ro.sapientia.furniture.repository.CupboardRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CupboardComponentTest {
    @Autowired MockMvc mockMvc;

    @Autowired
    CupboardRepository cupboardRepository;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        Cupboard cb = new Cupboard();
        cb.setHeight(500);
        cb.setWidth(500);
        cb.setNumberOfCab(5);
        cb.setNumberOfDrawer(5);
        var saveCB = cupboardRepository.save(cb);

        this.mockMvc.perform(get("/cupboard/all")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(500)))
                .andExpect(jsonPath("$[0].width", is(500)))
                .andExpect(jsonPath("$[0].numberOfCab", is(5)))
                .andExpect(jsonPath("$[0].numberOfDrawer", is(5)));
    }
}
