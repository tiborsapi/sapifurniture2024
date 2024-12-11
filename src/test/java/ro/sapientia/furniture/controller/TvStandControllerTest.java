package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.service.FurnitureBodyService;
import ro.sapientia.furniture.service.TvStandService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TvStandController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TvStandControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(TvStandService.class)
	private TvStandService tvStandService;


	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final Tvstand stand = new Tvstand();
		stand.setHeigth(10);
		when(tvStandService.findAllTvStand()).thenReturn(List.of(stand));

		this.mockMvc.perform(get("/tvstand/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(10)));
	}


}
