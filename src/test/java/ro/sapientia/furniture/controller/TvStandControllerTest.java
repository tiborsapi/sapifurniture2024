package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.repository.TvStandRepository;
import ro.sapientia.furniture.service.FurnitureBodyService;
import ro.sapientia.furniture.service.TvStandService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TvStandController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TvStandControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TvStandService tvstandService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetAllTvstands() throws Exception {
		final Tvstand stand = new Tvstand();
		stand.setHeigth(10);
		when(tvstandService.findAllTvStand()).thenReturn(List.of(stand));

		this.mockMvc.perform(get("/tvstand/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(10)));
	}

	@Test
	public void testCreateTvstand() throws Exception {
		Tvstand tvstand = new Tvstand();
		tvstand.setWidth(500);
		tvstand.setHeigth(500);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("wood");

		// Itt volt a hiba - nem a findAll-t kell mockolni, hanem a create-et
		when(tvstandService.create(any(Tvstand.class))).thenReturn(tvstand);

		mockMvc.perform(post("/tvstand/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tvstand)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.width", is(500)))
				.andExpect(jsonPath("$.heigth", is(500)))
				.andExpect(jsonPath("$.depth", is(300)))
				.andExpect(jsonPath("$.doors", is(2)))
				.andExpect(jsonPath("$.material", is("wood")));
	}

	@Test
	public void testGetAllTvstands2() throws Exception {
		Tvstand tvstand = new Tvstand();
		tvstand.setWidth(500);
		tvstand.setHeigth(500);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("wood");

		when(tvstandService.findAllTvStand()).thenReturn(List.of(tvstand));

		mockMvc.perform(get("/tvstand/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].width", is(500)))
				.andExpect(jsonPath("$[0].heigth", is(500)))
				.andExpect(jsonPath("$[0].depth", is(300)))
				.andExpect(jsonPath("$[0].doors", is(2)))
				.andExpect(jsonPath("$[0].material", is("wood")));
	}

	@Test
	public void testGetTvstandById() throws Exception {
		Tvstand tvstand = new Tvstand();
		tvstand.setId(1L);
		tvstand.setWidth(500);
		tvstand.setHeigth(500);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("wood");

		when(tvstandService.findTvstandById(1L)).thenReturn(tvstand);

		mockMvc.perform(get("/tvstand/find/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.width", is(500)))
				.andExpect(jsonPath("$.heigth", is(500)))
				.andExpect(jsonPath("$.depth", is(300)))
				.andExpect(jsonPath("$.doors", is(2)))
				.andExpect(jsonPath("$.material", is("wood")));
	}

	@Test
	public void testUpdateTvstand() throws Exception {
		Tvstand tvstand = new Tvstand();
		tvstand.setId(1L);
		tvstand.setWidth(600);
		tvstand.setHeigth(600);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("metal");

		// Az any() matchert használjuk itt is
		when(tvstandService.update(any(Tvstand.class))).thenReturn(tvstand);

		mockMvc.perform(post("/tvstand/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tvstand)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.width", is(600)))
				.andExpect(jsonPath("$.heigth", is(600)))
				.andExpect(jsonPath("$.depth", is(300)))
				.andExpect(jsonPath("$.doors", is(2)))
				.andExpect(jsonPath("$.material", is("metal")));
	}

	@Test
	public void testDeleteTvstand() throws Exception {
		Long id = 1L;

		mockMvc.perform(get("/tvstand/delete/{id}", id))
				.andExpect(status().isOk());
	}

	@Test
	public void testInvalidTvstandData() throws Exception {
		Tvstand tvstand = new Tvstand();
		tvstand.setWidth(-500);
		tvstand.setHeigth(-500);
		tvstand.setDepth(-300);
		tvstand.setDoors(-2);
		tvstand.setMaterial("");

		mockMvc.perform(post("/tvstand/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tvstand)))
				.andExpect(status().isBadRequest());
	}
}


