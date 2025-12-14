package ro.sapientia.furniture.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.service.ComponentListService;
import ro.sapientia.furniture.service.FurnitureBodyService;

@WebMvcTest(ComponentListController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
public class ComponentListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FurnitureBodyService persistenceService;

    @MockBean
    private FurnitureMapper furnitureMapper;

    @MockBean
    private ComponentListRepository componentListRepository;

    @MockBean
    private ComponentListService componentListService;

    private final ObjectMapper om = new ObjectMapper();

    @Test
    public void createFurnitureAndComponentList_returnsBadRequestWhenNoLists() throws Exception {
        FurnitureBodyDTO dto = new FurnitureBodyDTO();
        FurnitureBody fb = new FurnitureBody(); fb.setId(1L);

        when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(fb);
        when(persistenceService.saveFurnitureBodyAndComponents(any(FurnitureBody.class), any(Long.class))).thenReturn(fb);
        when(componentListRepository.findByFurnitureBody_Id(1L)).thenReturn(Collections.emptyList());

        mvc.perform(post("/api/furniture?createdBy=5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createFurnitureAndComponentList_returnsOkWhenListExists() throws Exception {
        FurnitureBodyDTO dto = new FurnitureBodyDTO();
        FurnitureBody fb = new FurnitureBody(); fb.setId(2L);
        ComponentList cl = new ComponentList(); cl.setId(10L);
        ComponentListDTO clDto = new ComponentListDTO(); clDto.setId(10L);

        when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(fb);
        when(persistenceService.saveFurnitureBodyAndComponents(any(FurnitureBody.class), any(Long.class))).thenReturn(fb);
        when(componentListRepository.findByFurnitureBody_Id(2L)).thenReturn(Arrays.asList(cl));
        when(furnitureMapper.toDto(cl)).thenReturn(clDto);

        mvc.perform(post("/api/furniture?createdBy=8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    public void getAllComponentLists_returnsList() throws Exception {
        ComponentList cl = new ComponentList(); cl.setId(20L);
        ComponentListDTO dto = new ComponentListDTO(); dto.setId(20L);
        when(componentListRepository.findAll()).thenReturn(Arrays.asList(cl));
        when(furnitureMapper.toDto(cl)).thenReturn(dto);

        mvc.perform(get("/api/component-lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(20));
    }

    @Test
    public void getComponentListById_notFoundWhenMissing() throws Exception {
        when(componentListRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        mvc.perform(get("/api/component-lists/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create_update_and_delete_componentList_flows() throws Exception {
        ComponentListDTO dto = new ComponentListDTO(); dto.setCreatedBy(3L);
        ComponentList saved = new ComponentList(); saved.setId(300L);
        ComponentListDTO savedDto = new ComponentListDTO(); savedDto.setId(300L);

        when(componentListService.create(any(ComponentListDTO.class))).thenReturn(saved);
        when(furnitureMapper.toDto(saved)).thenReturn(savedDto);

        mvc.perform(post("/api/component-lists").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(300));

        when(componentListService.update(any(Long.class), any(ComponentListDTO.class))).thenReturn(java.util.Optional.of(saved));
        when(furnitureMapper.toDto(saved)).thenReturn(savedDto);

        mvc.perform(put("/api/component-lists/300").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(300));

        when(componentListService.delete(300L)).thenReturn(true);

        mvc.perform(delete("/api/component-lists/300"))
                .andExpect(status().isNoContent());

        when(componentListService.delete(301L)).thenReturn(false);
        mvc.perform(delete("/api/component-lists/301"))
                .andExpect(status().isNotFound());
    }

        @Test
        public void controller_direct_methods_return500_whenServiceNull() {
                FurnitureBodyService p = Mockito.mock(FurnitureBodyService.class);
                FurnitureMapper m = Mockito.mock(FurnitureMapper.class);
                ComponentListRepository repo = Mockito.mock(ComponentListRepository.class);

                ComponentListController ctrl = new ComponentListController(p, m, repo, null);

                ComponentListDTO dto = new ComponentListDTO();

                ResponseEntity<ComponentListDTO> resCreate = ctrl.createComponentList(dto);
                assertEquals(500, resCreate.getStatusCodeValue());

                ResponseEntity<ComponentListDTO> resUpdate = ctrl.updateComponentList(1L, dto);
                assertEquals(500, resUpdate.getStatusCodeValue());

                ResponseEntity<Void> resDelete = ctrl.deleteComponentList(1L);
                assertEquals(500, resDelete.getStatusCodeValue());
        }
}
