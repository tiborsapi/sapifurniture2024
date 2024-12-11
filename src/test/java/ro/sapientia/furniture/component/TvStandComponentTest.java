package ro.sapientia.furniture.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.TvStandRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TvStandComponentTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	TvStandRepository repository;
	
	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		Tvstand ts = new Tvstand();
		ts.setHeigth(20);
		ts.setWidth(10);
		ts.setDepth(6);
		ts.setDoors(2);
		ts.setMaterial("wood");
		var savedFB = repository.save(ts);

		this.mockMvc.perform(get("/tvstand/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(20)))
				.andExpect(jsonPath("$[0].width", is(10)))
				.andExpect(jsonPath("$[0].depth", is(6)))
				.andExpect(jsonPath("$[0].doors", is(2)))
				.andExpect(jsonPath("$[0].material", is("wood")));
	}

}
