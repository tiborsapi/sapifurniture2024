package ro.sapientia.furniture.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.service.PersistenceService;

class ComponentListControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PersistenceService persistenceService;
    @Mock
    private FurnitureMapper furnitureMapper;
    @Mock
    private ComponentListRepository componentListRepository;

    private ComponentListController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new ComponentListController(persistenceService, furnitureMapper, componentListRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createFurnitureAndComponentList_success() throws Exception {
        FurnitureBodyDTO dto = new FurnitureBodyDTO();
        // set DTO fields as needed, here we leave empty for the test
        FurnitureBody entity = new FurnitureBody();
        entity.setId(null);
        FurnitureBody saved = new FurnitureBody();
        saved.setId(10L);

        ComponentList cl = new ComponentList();
        cl.setId(100L);
        ComponentListDTO clDto = new ComponentListDTO();
        clDto.setId(100L);

        when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(entity);
        when(persistenceService.saveFurnitureBodyAndComponents(any(FurnitureBody.class), anyLong())).thenReturn(saved);
        when(componentListRepository.findByFurnitureBody_Id(saved.getId())).thenReturn(Arrays.asList(cl));
        when(furnitureMapper.toDto(cl)).thenReturn(clDto);

        mockMvc.perform(post("/api/furniture")
                .param("createdBy", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(100));

        verify(persistenceService).saveFurnitureBodyAndComponents(any(FurnitureBody.class), eq(1L));
    }

    @Test
    void createFurnitureAndComponentList_noComponentLists_returnsBadRequest() throws Exception {
        FurnitureBodyDTO dto = new FurnitureBodyDTO();
        FurnitureBody entity = new FurnitureBody();
        entity.setId(null);
        FurnitureBody saved = new FurnitureBody();
        saved.setId(11L);

        when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(entity);
        when(persistenceService.saveFurnitureBodyAndComponents(any(FurnitureBody.class), anyLong())).thenReturn(saved);
        when(componentListRepository.findByFurnitureBody_Id(saved.getId())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/furniture")
                .param("createdBy", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());

        verify(persistenceService).saveFurnitureBodyAndComponents(any(FurnitureBody.class), eq(2L));
    }

    @Test
    void getAllComponentLists_returnsList() throws Exception {
        ComponentList cl1 = new ComponentList();
        cl1.setId(1L);
        ComponentListDTO dto1 = new ComponentListDTO();
        dto1.setId(1L);
        when(componentListRepository.findAll()).thenReturn(Arrays.asList(cl1));
        when(furnitureMapper.toDto(cl1)).thenReturn(dto1);

        mockMvc.perform(get("/api/component-lists"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getComponentListById_found() throws Exception {
        ComponentList cl = new ComponentList();
        cl.setId(5L);
        ComponentListDTO dto = new ComponentListDTO();
        dto.setId(5L);

        when(componentListRepository.findById(5L)).thenReturn(Optional.of(cl));
        when(furnitureMapper.toDto(cl)).thenReturn(dto);

        mockMvc.perform(get("/api/component-lists/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void getComponentListById_notFound() throws Exception {
        when(componentListRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/component-lists/999"))
            .andExpect(status().isNotFound());
    }
}