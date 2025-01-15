package ro.sapientia.furniture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.RockingChairModel;
import ro.sapientia.furniture.service.RockingChairService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RockingChairController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class RockingChairControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(RockingChairService.class)
    private RockingChairService rockingChairService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllRockingChairs() throws Exception {
        when(rockingChairService.findAllRockingCharis()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rocking-chairs"))
                .andExpect(status().isOk());

        verify(rockingChairService, times(1)).findAllRockingCharis();
    }

    @Test
    public void testFindRockingChairById() throws Exception {
        RockingChairModel chair = new RockingChairModel();
        chair.setId(1L);

        when(rockingChairService.findRockingChairById(1L)).thenReturn(List.of(chair));

        mockMvc.perform(get("/rocking-chairs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(rockingChairService, times(1)).findRockingChairById(1L);
    }

    @Test
    public void testFindRockingChairByMaterial() throws Exception {
        RockingChairModel chair = new RockingChairModel();
        chair.setMaterial("Wood");

        when(rockingChairService.findRockingChairByMaterial("Wood")).thenReturn(new ArrayList<>(List.of(chair)));

        mockMvc.perform(get("/rocking-chairs/material/Wood"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].material").value("Wood"));

        verify(rockingChairService, times(1)).findRockingChairByMaterial("Wood");
    }

    @Test
    public void testFindRockingChairsByRockingAngle() throws Exception {
        RockingChairModel chair = new RockingChairModel();
        chair.setRockerRadius(60.0);

        when(rockingChairService.findRockingChairByRockingRadius(45.0, 75.0)).thenReturn(new ArrayList<>(List.of(chair)));

        mockMvc.perform(get("/rocking-chairs/rockingAngle").param("minAngle", "45.0").param("maxAngle", "75.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rockerRadius").value(60.0));

        verify(rockingChairService, times(1)).findRockingChairByRockingRadius(45.0, 75.0);
    }

    @Test
    public void testCreateRockingChair() throws Exception {
        RockingChairModel chair = new RockingChairModel();
        chair.setMaterial("Metal");

        when(rockingChairService.saveRockingChair(any(RockingChairModel.class))).thenReturn(chair);

        mockMvc.perform(post("/rocking-chairs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chair)))
                .andExpect(status().isCreated());

        verify(rockingChairService, times(1)).saveRockingChair(any(RockingChairModel.class));
    }


}
