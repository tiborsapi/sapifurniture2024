package ro.sapientia.furniture.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.HammockBody;
import ro.sapientia.furniture.repository.HammockBodyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HammockBodyComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    HammockBodyRepository hammockBodyRepository;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        HammockBody hammockBody = new HammockBody();
        hammockBody.setLength(300);
        hammockBody.setWidth(150);
        hammockBody.setWeight(0.54);
        hammockBody.setMaterial("Polyester");
        var savedHammockBody = hammockBodyRepository.save(hammockBody);

        this.mockMvc.perform(get("/hammock/all")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].length", is(300.0)));
    }
}
