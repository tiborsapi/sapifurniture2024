package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.FurnitureWineRack;
import ro.sapientia.furniture.service.FurnitureWineRackService;

import java.util.List;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FurnitureWineRackController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class FurnitureWineRackControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(FurnitureWineRackService.class)
	private FurnitureWineRackService furnitureWineRackService;

	@Test
	public void findAllWineRack() throws Exception {
		var wineRack = new FurnitureWineRack();
		wineRack.setCapacity(100);

		when(furnitureWineRackService.findAllFurnitureWineRack()).thenReturn(List.of(wineRack));


		this.mockMvc.perform(get("/winerack")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].capacity", is(100)));
	}


	@Test
	public void findAllFurnitureWineRackBetweenTwoCapacityExceptBadRequest() throws Exception {
		this.mockMvc.perform(get("/winerack/capacity/alma/10"))
				.andExpect(status().isBadRequest());
	}


}
