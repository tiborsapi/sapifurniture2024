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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.chair.controller.FurnitureChairController;
import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.service.FurnitureChairService;

@WebMvcTest(controllers = FurnitureChairController.class, excludeAutoConfiguration = {
		SecurityAutoConfiguration.class })
public class FurnitureOtherControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(FurnitureChairService.class)
	private FurnitureChairService FurnitureChairService;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final FurnitureChair furnitureChair = new FurnitureChair();

		furnitureChair.setName("impaled");

		when(FurnitureChairService.findAllFurnitureChairs()).thenReturn(List.of(furnitureChair));

		this.mockMvc.perform(get("/furniter_other/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("impaled")));

	}

}
