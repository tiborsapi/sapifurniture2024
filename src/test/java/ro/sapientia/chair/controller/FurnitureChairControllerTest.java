package ro.sapientia.chair.controller;

import static org.hamcrest.CoreMatchers.is;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.chair.controller.FurnitureChairController;
import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.service.FurnitureChairService;
import ro.sapientia.furniture.FurnitureApplication;

@ContextConfiguration(classes = FurnitureApplication.class)
@WebMvcTest(controllers = FurnitureChairController.class, excludeAutoConfiguration = {
		SecurityAutoConfiguration.class })
public class FurnitureChairControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(FurnitureChairService.class)
	private FurnitureChairService furnitureChairService;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final FurnitureChair furnitureChair = new FurnitureChair();

		furnitureChair.setName("impaled");

		when(furnitureChairService.findAllFurnitureChairs()).thenReturn(List.of(furnitureChair));

		this.mockMvc.perform(get("/furniture_chair/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("impaled")));

	}

}
