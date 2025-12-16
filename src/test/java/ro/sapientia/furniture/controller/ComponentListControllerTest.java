package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.service.PersistenceService;

@TestPropertySource(locations = "classpath:mvc.properties")
@WebMvcTest(controllers = ComponentListController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class ComponentListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersistenceService persistenceService;

	@MockBean
	private FurnitureMapper furnitureMapper;

	@MockBean
	private ComponentListRepository componentListRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetAllComponentLists() throws Exception {
		ComponentList cl1 = new ComponentList();
		cl1.setId(1L);
		cl1.setCreatedBy(100L);
		cl1.setCreatedAt(LocalDateTime.now());
		cl1.setUpdatedAt(LocalDateTime.now());

		ComponentList cl2 = new ComponentList();
		cl2.setId(2L);
		cl2.setCreatedBy(200L);
		cl2.setCreatedAt(LocalDateTime.now());
		cl2.setUpdatedAt(LocalDateTime.now());

		ComponentListDTO dto1 = new ComponentListDTO();
		dto1.setId(1L);
		dto1.setCreatedBy(100L);

		ComponentListDTO dto2 = new ComponentListDTO();
		dto2.setId(2L);
		dto2.setCreatedBy(200L);

		when(componentListRepository.findAll()).thenReturn(List.of(cl1, cl2));
		when(furnitureMapper.toDto(cl1)).thenReturn(dto1);
		when(furnitureMapper.toDto(cl2)).thenReturn(dto2);

		this.mockMvc.perform(get("/api/component-lists"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].createdBy", is(100)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].createdBy", is(200)));
	}

	@Test
	public void testGetComponentListById() throws Exception {
		ComponentList cl = new ComponentList();
		cl.setId(1L);
		cl.setCreatedBy(100L);
		cl.setCreatedAt(LocalDateTime.now());
		cl.setUpdatedAt(LocalDateTime.now());

		ComponentListDTO dto = new ComponentListDTO();
		dto.setId(1L);
		dto.setCreatedBy(100L);

		when(componentListRepository.findById(1L)).thenReturn(Optional.of(cl));
		when(furnitureMapper.toDto(cl)).thenReturn(dto);

		this.mockMvc.perform(get("/api/component-lists/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.createdBy", is(100)));
	}

	@Test
	public void testGetComponentListByIdNotFound() throws Exception {
		when(componentListRepository.findById(999L)).thenReturn(Optional.empty());

		this.mockMvc.perform(get("/api/component-lists/999"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreateFurnitureAndComponentList() throws Exception {
		FurnitureBodyDTO furnitureDto = new FurnitureBodyDTO();
		furnitureDto.setWidth(100);
		furnitureDto.setHeigth(200);
		furnitureDto.setDepth(50);
		furnitureDto.setThickness(18);

		FurnitureBody savedFb = new FurnitureBody();
		savedFb.setId(1L);
		savedFb.setWidth(100);
		savedFb.setHeigth(200);
		savedFb.setDepth(50);
		savedFb.setThickness(18);

		ComponentList cl = new ComponentList();
		cl.setId(1L);
		cl.setCreatedBy(100L);
		cl.setFurnitureBody(savedFb);
		cl.setCreatedAt(LocalDateTime.now());
		cl.setUpdatedAt(LocalDateTime.now());

		ComponentListDTO responseDto = new ComponentListDTO();
		responseDto.setId(1L);
		responseDto.setCreatedBy(100L);
		responseDto.setFurnitureBodyId(1L);

		when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(savedFb);
		when(persistenceService.saveFurnitureBodyAndComponents(any(FurnitureBody.class), anyLong())).thenReturn(savedFb);
		when(componentListRepository.findByFurnitureBody_Id(1L)).thenReturn(List.of(cl));
		when(furnitureMapper.toDto(cl)).thenReturn(responseDto);

		this.mockMvc.perform(post("/api/furniture")
				.param("createdBy", "100")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(furnitureDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.createdBy", is(100)))
				.andExpect(jsonPath("$.furnitureBodyId", is(1)));
	}

	@Test
	public void testCreateFurnitureAndComponentListEmptyList() throws Exception {
		FurnitureBodyDTO furnitureDto = new FurnitureBodyDTO();
		furnitureDto.setWidth(100);
		furnitureDto.setHeigth(200);

		FurnitureBody savedFb = new FurnitureBody();
		savedFb.setId(1L);

		when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(savedFb);
		when(persistenceService.saveFurnitureBodyAndComponents(any(FurnitureBody.class), anyLong())).thenReturn(savedFb);
		when(componentListRepository.findByFurnitureBody_Id(1L)).thenReturn(new ArrayList<>());

		this.mockMvc.perform(post("/api/furniture")
				.param("createdBy", "100")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(furnitureDto)))
				.andExpect(status().isBadRequest());
	}
}

