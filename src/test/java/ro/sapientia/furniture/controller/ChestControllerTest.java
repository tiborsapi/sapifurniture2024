package ro.sapientia.furniture.controller;

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

import ro.sapientia.furniture.model.Chest;
import ro.sapientia.furniture.service.ChestService;

@WebMvcTest(controllers = ChestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ChestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(ChestService.class)
	private ChestService chestService;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final Chest chest = new Chest();
		chest.setHeigth(10);
		chest.setWidth(5);
		chest.setDepth(4);
		chest.setType("Bachelors");
		when(chestService.findAllChests()).thenReturn(List.of(chest));

		this.mockMvc.perform(get("/chest/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(10)))
				.andExpect(jsonPath("$[0].width" , is(5)))
				.andExpect(jsonPath("$[0].depth" , is(4)))
				.andExpect(jsonPath("$[0].type" , is("Bachelors")));
	}
}
