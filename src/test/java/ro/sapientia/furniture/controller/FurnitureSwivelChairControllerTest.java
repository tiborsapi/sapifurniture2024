package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.model.FurnitureSwivelChair;
import ro.sapientia.furniture.service.FurnitureSwivelChairService;

@WebMvcTest(controllers = FurnitureSwivelChairController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class FurnitureSwivelChairControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(FurnitureSwivelChairService.class)
    private FurnitureSwivelChairService furnitureSwivelChairService;

    @Test
    public void testFindAllSwivelChairs() throws Exception {
        var swivelChair = new FurnitureSwivelChair();
        swivelChair.setBackrestHeight(70);

        when(furnitureSwivelChairService.findAllFurnitureSwivelChairs()).thenReturn(List.of(swivelChair));

        this.mockMvc.perform(get("/swivelchair/all")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].backrestHeight", is(70)));
    }

    @Test
    public void testFindAllSwivelChairsByWeightCapacity() throws Exception {
        var swivelChair = new FurnitureSwivelChair();
        int weightCapacity = 150;
        swivelChair.setWeightCapacity(weightCapacity);

        when(furnitureSwivelChairService.findAllFurnitureSwivelChairByWeightCapacity(150)).thenReturn(List.of(swivelChair));

        this.mockMvc.perform(get("/swivelchair/weightcapacity/" + weightCapacity)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].weightCapacity", is(150)));
    }

    @Test
    public void testFindAllSwivelChairsByMaterial() throws Exception {
        var swivelChair = new FurnitureSwivelChair();
        swivelChair.setMaterial("plastic");

        when(furnitureSwivelChairService.findAllFurnitureSwivelChairByMaterial("plastic")).thenReturn(List.of(swivelChair));

        this.mockMvc.perform(get("/swivelchair/material/plastic")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].material", is("plastic")));
    }

    @Test
    public void testAddFurnitureSwivelChair() throws Exception {
        var swivelChair = new FurnitureSwivelChair();

        swivelChair.setSeatWidth(30);
        swivelChair.setSeatDepth(40);
        swivelChair.setBackrestHeight(50);
        swivelChair.setWeightCapacity(100);
        swivelChair.setMaterial("plastic");

        when(furnitureSwivelChairService.create(any(FurnitureSwivelChair.class))).thenReturn(swivelChair);

        this.mockMvc.perform(post("/swivelchair/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"seatWidth\":30,\"seatDepth\":40,\"backrestHeight\":50,\"weightCapacity\":100,\"material\":\"plastic\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.backrestHeight", is(50)))
                    .andExpect(jsonPath("$.weightCapacity", is(100)))
                    .andExpect(jsonPath("$.seatWidth", is(30)))
                    .andExpect(jsonPath("$.seatDepth", is(40)))
                    .andExpect(jsonPath("$.material", is("plastic")));
    }

    @Test
    public void testUpdateFurnitureSwivelChair() throws Exception {
        var updatedSwivelChair = new FurnitureSwivelChair();

        updatedSwivelChair.setId(1L);
        updatedSwivelChair.setSeatWidth(30);
        updatedSwivelChair.setSeatDepth(40);
        updatedSwivelChair.setBackrestHeight(50);
        updatedSwivelChair.setWeightCapacity(100);
        updatedSwivelChair.setMaterial("plastic");

        when(furnitureSwivelChairService.update(any(FurnitureSwivelChair.class))).thenReturn(updatedSwivelChair);

        this.mockMvc.perform(post("/swivelchair/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\":1,\"seatWidth\":30,\"seatDepth\":40,\"weightCapacity\":100,\"material\":\"plastic\",\"backrestHeight\":50}"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.backrestHeight", is(50)))
                    .andExpect(jsonPath("$.weightCapacity", is(100)))
                    .andExpect(jsonPath("$.seatWidth", is(30)))
                    .andExpect(jsonPath("$.seatDepth", is(40)))
                    .andExpect(jsonPath("$.material", is("plastic")))
                    .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testDeleteFurnitureSwivelChair() throws Exception {
        Long id = 1L;

        this.mockMvc.perform(get("/swivelchair/delete/" + id)).andExpect(status().isOk())
                .andExpect(status().isOk());
    }

}
