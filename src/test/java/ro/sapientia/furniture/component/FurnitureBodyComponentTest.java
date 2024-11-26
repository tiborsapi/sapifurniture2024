package ro.sapientia.furniture.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.controller.FurnitureController;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
@WebMvcTest(controllers = FurnitureController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class FurnitureBodyComponentTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
    FurnitureBodyRepository repository;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final FurnitureBody body = new FurnitureBody();
		body.setHeigth(10);
		when(furnitureBodyService.findAllFurnitureBodies()).thenReturn(List.of(body));

		this.mockMvc.perform(get("/furniture/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(10)));
	}
	
}
