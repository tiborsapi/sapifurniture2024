package ro.sapientia.furniture.component;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.model.FurnitureStopper;
import ro.sapientia.furniture.repository.FurnitureStopperRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FurnitureStopperComponentTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FurnitureStopperRepository furnitureStopperRepository;

	@Test
	public void testFindAllFurnitureStoppers() throws Exception {
		mockMvc.perform(get("/furniture-stopper/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").exists());
	}

	@Test
	public void testFindFurnitureStopperById() throws Exception {
		FurnitureStopper stopper = new FurnitureStopper();
		stopper.setStartTimeMs(420);
		furnitureStopperRepository.saveAndFlush(stopper);

		mockMvc.perform(get("/furniture-stopper/find/" + stopper.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.start_time_ms", is(420)));
	}

	@Test
	public void testCreateFurnitureStopper() throws Exception {
		FurnitureStopper stopper = new FurnitureStopper();
		stopper.setStartTimeMs(500);

		mockMvc.perform(post("/furniture-stopper/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(stopper)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.start_time_ms", is(500)));
	}

	@Test
	public void testUpdateFurnitureStopper() throws Exception {
		FurnitureStopper stopper = new FurnitureStopper();
		stopper.setStartTimeMs(420);
		furnitureStopperRepository.saveAndFlush(stopper);

		stopper.setStartTimeMs(600);

		mockMvc.perform(post("/furniture-stopper/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(stopper)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.start_time_ms", is(600)));
	}

	@Test
	public void testDeleteFurnitureStopper() throws Exception {
		FurnitureStopper stopper = new FurnitureStopper();
		stopper.setStartTimeMs(420);
		furnitureStopperRepository.saveAndFlush(stopper);

		mockMvc.perform(get("/furniture-stopper/delete/" + stopper.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}