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

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.HallTreeBody;
import ro.sapientia.furniture.repository.HallTreeBodyRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HallTreeBodyComponentTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	HallTreeBodyRepository repository;
	
	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		HallTreeBody hb = new HallTreeBody();
		hb.setHeigth(20);
		hb.setWidth(10);
		hb.setDepth(6);
		var savedHB = repository.save(hb);

		this.mockMvc.perform(get("/hallTree/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(20)));
	}
	
	@Test
    public void findByIdTest() throws Exception {
        HallTreeBody hb = new HallTreeBody();
        hb.setId(1L);
        hb.setHeigth(20);
        hb.setWidth(10);
        hb.setDepth(6);

        repository.save(hb);

        this.mockMvc.perform(get("/hallTree/find/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }
}
