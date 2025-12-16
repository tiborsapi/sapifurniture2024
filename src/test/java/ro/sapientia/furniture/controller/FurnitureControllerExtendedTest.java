package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.service.FurnitureBodyService;

@TestPropertySource(locations = "classpath:mvc.properties")
@WebMvcTest(controllers = FurnitureController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class FurnitureControllerExtendedTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FurnitureBodyService furnitureBodyService;

	@MockBean
	private FurnitureMapper furnitureMapper;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetFurnitureBodyById() throws Exception {
		FurnitureBody body = new FurnitureBody();
		body.setId(1L);
		body.setHeigth(20);
		body.setWidth(10);
		body.setDepth(6);
		body.setThickness(18);

		FurnitureBodyDTO dto = new FurnitureBodyDTO();
		dto.setId(1L);
		dto.setHeigth(20);
		dto.setWidth(10);
		dto.setDepth(6);
		dto.setThickness(18);

		when(furnitureBodyService.findFurnitureBodyById(1L)).thenReturn(body);
		when(furnitureMapper.toDto(body)).thenReturn(dto);

		this.mockMvc.perform(get("/furniture/find/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.heigth", is(20)))
				.andExpect(jsonPath("$.width", is(10)))
				.andExpect(jsonPath("$.depth", is(6)))
				.andExpect(jsonPath("$.thickness", is(18)));
	}

	@Test
	public void testAddFurnitureBody() throws Exception {
		FurnitureBodyDTO requestDto = new FurnitureBodyDTO();
		requestDto.setHeigth(20);
		requestDto.setWidth(10);
		requestDto.setDepth(6);
		requestDto.setThickness(18);

		FurnitureBody entity = new FurnitureBody();
		entity.setHeigth(20);
		entity.setWidth(10);
		entity.setDepth(6);
		entity.setThickness(18);

		FurnitureBody savedEntity = new FurnitureBody();
		savedEntity.setId(1L);
		savedEntity.setHeigth(20);
		savedEntity.setWidth(10);
		savedEntity.setDepth(6);
		savedEntity.setThickness(18);

		FurnitureBodyDTO responseDto = new FurnitureBodyDTO();
		responseDto.setId(1L);
		responseDto.setHeigth(20);
		responseDto.setWidth(10);
		responseDto.setDepth(6);
		responseDto.setThickness(18);

		when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(entity);
		when(furnitureBodyService.create(entity)).thenReturn(savedEntity);
		when(furnitureMapper.toDto(savedEntity)).thenReturn(responseDto);

		this.mockMvc.perform(post("/furniture/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.heigth", is(20)));
	}

	@Test
	public void testUpdateFurnitureBody() throws Exception {
		FurnitureBodyDTO requestDto = new FurnitureBodyDTO();
		requestDto.setId(1L);
		requestDto.setHeigth(25);
		requestDto.setWidth(15);
		requestDto.setDepth(8);
		requestDto.setThickness(20);

		FurnitureBody entity = new FurnitureBody();
		entity.setId(1L);
		entity.setHeigth(25);
		entity.setWidth(15);
		entity.setDepth(8);
		entity.setThickness(20);

		FurnitureBody updatedEntity = new FurnitureBody();
		updatedEntity.setId(1L);
		updatedEntity.setHeigth(25);
		updatedEntity.setWidth(15);
		updatedEntity.setDepth(8);
		updatedEntity.setThickness(20);

		FurnitureBodyDTO responseDto = new FurnitureBodyDTO();
		responseDto.setId(1L);
		responseDto.setHeigth(25);
		responseDto.setWidth(15);
		responseDto.setDepth(8);
		responseDto.setThickness(20);

		when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(entity);
		when(furnitureBodyService.update(entity)).thenReturn(updatedEntity);
		when(furnitureMapper.toDto(updatedEntity)).thenReturn(responseDto);

		this.mockMvc.perform(post("/furniture/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.heigth", is(25)));
	}

	@Test
	public void testDeleteFurnitureBody() throws Exception {
		this.mockMvc.perform(get("/furniture/delete/1"))
				.andExpect(status().isOk());
	}
}

