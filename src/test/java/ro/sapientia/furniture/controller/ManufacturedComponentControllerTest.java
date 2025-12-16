package ro.sapientia.furniture.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.dto.ManufacturedComponentDTO;
import ro.sapientia.furniture.model.ManufacturedComponent;
import ro.sapientia.furniture.model.ManufacturedComponentType;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.service.ManufacturedComponentService;

@WebMvcTest(ManufacturedComponentController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
public class ManufacturedComponentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManufacturedComponentService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ManufacturedComponent component;
    private ComponentList componentList;
    private ManufacturedComponentType type;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        componentList = new ComponentList();
        componentList.setId(1L);

        type = new ManufacturedComponentType();
        type.setId(1L);
        type.setName("Wood");

        component = new ManufacturedComponent(componentList, type, 5);
        component.setId(10L);
    }

    @Test
    void list_returnsComponents() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(component));

        mvc.perform(get("/api/manufactured-components"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(10L)).thenReturn(component);

        mvc.perform(get("/api/manufactured-components/10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void getById_notFound() throws Exception {
        when(service.findById(999L)).thenReturn(null);

        mvc.perform(get("/api/manufactured-components/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        ManufacturedComponentDTO dto = new ManufacturedComponentDTO();
        dto.setComponentListId(1L);
        dto.setManufacturedComponentTypeId(1L);
        dto.setQuantity(5);

        when(service.create(any(ManufacturedComponentDTO.class))).thenReturn(component);

        mvc.perform(post("/api/manufactured-components")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void update_success() throws Exception {
        ManufacturedComponentDTO dto = new ManufacturedComponentDTO();
        dto.setQuantity(8);
        dto.setComponentListId(1L);
        dto.setManufacturedComponentTypeId(1L);
    
        component.setQuantity(8);
        when(service.update(eq(10L), any(ManufacturedComponentDTO.class))).thenReturn(component);
    
        mvc.perform(put("/api/manufactured-components/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.quantity").value(8));
    }
    

    @Test
    void delete_success() throws Exception {
        doNothing().when(service).delete(10L);

        mvc.perform(delete("/api/manufactured-components/10"))
            .andExpect(status().isNoContent());
    }

    @Test
    void byComponentList_returnsComponents() throws Exception {
        when(service.findByComponentListId(1L)).thenReturn(Arrays.asList(component));

        mvc.perform(get("/api/manufactured-components/by-component-list/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void byType_returnsComponents() throws Exception {
        when(service.findByTypeId(1L)).thenReturn(Arrays.asList(component));

        mvc.perform(get("/api/manufactured-components/by-type/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void list_empty_returnsEmptyList() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/manufactured-components"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void update_notFound() throws Exception {
        ManufacturedComponentDTO dto = new ManufacturedComponentDTO();
        dto.setQuantity(8);
        dto.setComponentListId(1L);
        dto.setManufacturedComponentTypeId(1L);

        when(service.update(eq(999L), any(ManufacturedComponentDTO.class))).thenReturn(null);

        mvc.perform(put("/api/manufactured-components/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());
    }

}
