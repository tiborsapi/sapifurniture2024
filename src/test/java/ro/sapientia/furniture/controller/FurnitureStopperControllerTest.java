package ro.sapientia.furniture.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.dto.StopFurnitureRequestDTO;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FurnitureStopper;
import ro.sapientia.furniture.service.FurnitureStopperService;

@WebMvcTest(controllers = FurnitureStopperController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class })
public class FurnitureStopperControllerTest {

    @MockBean(FurnitureStopperService.class)
    private FurnitureStopperService furnitureStopperService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllFurnitureStoppers() throws Exception {
        FurnitureStopper stopper1 = new FurnitureStopper();
        stopper1.setId(1L);
        FurnitureStopper stopper2 = new FurnitureStopper();
        stopper2.setId(2L);

        List<FurnitureStopper> stoppers = Arrays.asList(stopper1, stopper2);

        when(furnitureStopperService.findAllFurnitureStoppers()).thenReturn(stoppers);

        mockMvc.perform(get("/furniture-stopper/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(stopper1.getId()))
                .andExpect(jsonPath("$[1].id").value(stopper2.getId()));
    }

    @Test
    public void testGetFurnitureStopperById() throws Exception {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setId(1L);

        when(furnitureStopperService.findFurnitureStopperById(1L)).thenReturn(Optional.of(stopper));

        mockMvc.perform(get("/furniture-stopper/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stopper.getId()));
    }

    @Test
    public void testAddFurnitureStopper() throws Exception {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setActive(false);
        stopper.setStartTimeMs(0);
        stopper.setStopTimeMs(500);

        when(furnitureStopperService.create(stopper)).thenReturn(stopper);

        mockMvc.perform(post("/furniture-stopper/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(stopper)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateFurnitureStopper() throws Exception {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setActive(false);
        stopper.setStartTimeMs(0);
        stopper.setStopTimeMs(500);

        when(furnitureStopperService.update(stopper)).thenReturn(stopper);

        mockMvc.perform(post("/furniture-stopper/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(stopper)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFurnitureStopperById() throws Exception {
        mockMvc.perform(get("/furniture-stopper/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFurnitureStopperByIdNotFound() throws Exception {
        when(furnitureStopperService.findFurnitureStopperById(1L)).thenReturn(
                Optional.empty());

        mockMvc.perform(get("/furniture-stopper/find/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllFurnitureStoppersEmpty() throws Exception {
        when(furnitureStopperService.findAllFurnitureStoppers()).thenReturn(List.of());

        mockMvc.perform(get("/furniture-stopper/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testStopFurniture() throws Exception {
        StopFurnitureRequestDTO requestDTO = new StopFurnitureRequestDTO(1L, 1L);

        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setId(1L);

        when(furnitureStopperService.stopFurniture(anyLong(), any(FurnitureBody.class)))
                .thenReturn(Optional.of(stopper));

        mockMvc.perform(post("/furniture-stopper/stop")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stopper.getId()));
    }
}
