package ro.sapientia.chair.component;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.chair.repository.FurnitureChairRepository;
import ro.sapientia.chair.model.FurnitureChair;

@SpringBootTest(classes = FurnitureChairComponentTest.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FurnitureChairComponentTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	FurnitureChairRepository repository;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		FurnitureChair fo = new FurnitureChair();

		fo.setName("first");
		fo.setMaterial("wood");
		fo.setNumOfLegs(3);

		var savedFO = repository.save(fo);

		this.mockMvc.perform(get("/furniture_chair/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].numOfLegs", is(3)));
	}

}
