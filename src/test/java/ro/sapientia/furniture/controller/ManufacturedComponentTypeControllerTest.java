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

import ro.sapientia.furniture.model.ManufacturedComponentType;
import ro.sapientia.furniture.service.ManufacturedComponentTypeService;

@WebMvcTest(ManufacturedComponentTypeController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
public class ManufacturedComponentTypeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManufacturedComponentTypeService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ManufacturedComponentType type;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        type = new ManufacturedComponentType();
        type.setId(1L);
        type.setName("Wood");
    }

    @Test
    void list_returnsTypes() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(type));

        mvc.perform(get("/api/manufactured-component-types"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Wood"));
    }

    @Test
    void list_empty_returnsEmptyList() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/manufactured-component-types"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getById_found() throws Exception {
        when(service.findById(1L)).thenReturn(type);

        mvc.perform(get("/api/manufactured-component-types/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Wood"));
    }

    @Test
    void getById_notFound() throws Exception {
        when(service.findById(999L)).thenReturn(null);

        mvc.perform(get("/api/manufactured-component-types/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        ManufacturedComponentType input = new ManufacturedComponentType();
        input.setName("Metal");

        ManufacturedComponentType created = new ManufacturedComponentType();
        created.setId(2L);
        created.setName("Metal");

        when(service.create(any(ManufacturedComponentType.class))).thenReturn(created);

        mvc.perform(post("/api/manufactured-component-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Metal"));
    }

    @Test
    void update_success() throws Exception {
        ManufacturedComponentType input = new ManufacturedComponentType();
        input.setName("Plastic");

        type.setName("Plastic");
        when(service.update(eq(1L), any(ManufacturedComponentType.class))).thenReturn(type);

        mvc.perform(put("/api/manufactured-component-types/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Plastic"));
    }

    @Test
    void update_notFound() throws Exception {
        ManufacturedComponentType input = new ManufacturedComponentType();
        input.setName("Glass");

        when(service.update(eq(999L), any(ManufacturedComponentType.class))).thenReturn(null);

        mvc.perform(put("/api/manufactured-component-types/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isNotFound());
    }

    @Test
    void delete_success() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/manufactured-component-types/1"))
            .andExpect(status().isNoContent());
    }
}
