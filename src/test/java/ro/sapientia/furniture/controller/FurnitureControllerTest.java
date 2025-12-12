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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.service.FurnitureBodyService;

@TestPropertySource(locations = "classpath:mvc.properties")
@WebMvcTest(controllers = FurnitureController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class FurnitureControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FurnitureBodyService furnitureBodyService;

	@MockBean
	private FurnitureMapper furnitureMapper;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final FurnitureBody body = new FurnitureBody();
		body.setHeigth(10);
		final FurnitureBodyDTO dto = new FurnitureBodyDTO();
		dto.setHeigth(10);
		when(furnitureBodyService.findAllFurnitureBodies()).thenReturn(List.of(body));
		when(furnitureMapper.toDto(body)).thenReturn(dto);

		this.mockMvc.perform(get("/furniture/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(10)));
	}
}
