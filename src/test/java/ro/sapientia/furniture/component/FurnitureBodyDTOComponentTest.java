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

import ro.sapientia.furniture.model.entities.FurnitureBody;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class FurnitureBodyDTOComponentTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	FurnitureBodyRepository repository;
	
	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		FurnitureBody fb = new FurnitureBody();
		fb.setHeight(20);
		fb.setWidth(10);
		fb.setDepth(6);
		var savedFB = repository.save(fb);

		this.mockMvc.perform(get("/furniture/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].height", is(20)));
	}

}
