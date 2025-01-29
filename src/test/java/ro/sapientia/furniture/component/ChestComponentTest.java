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

import ro.sapientia.furniture.model.Chest;
import ro.sapientia.furniture.repository.ChestRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ChestComponentTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ChestRepository repository;
	
	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		Chest chest = new Chest();
		chest.setHeigth(20);
		chest.setWidth(10);
		chest.setDepth(6);
		chest.setType("Bachelors");
		var savedChest = repository.save(chest);

		this.mockMvc.perform(get("/chest/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(20)))
				.andExpect(jsonPath("$[0].width" , is(10)))
				.andExpect(jsonPath("$[0].depth" , is(6)))
				.andExpect(jsonPath("$[0].type" , is("Bachelors")));
	}

}