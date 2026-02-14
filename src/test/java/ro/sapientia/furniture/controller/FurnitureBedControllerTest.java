package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;
import ro.sapientia.furniture.service.FurnitureBedService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FurnitureBedController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class FurnitureBedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(FurnitureBedService.class)
    private FurnitureBedService furnitureBedService;

    @Test
    public void testFindAllService() throws Exception {
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        FurnitureBed bed2 = new FurnitureBed();
        bed2.setHeight(100);
        when(furnitureBedService.findAllBeds()).thenReturn(List.of(bed1, bed2));

        this.mockMvc.perform(get("/beds/all")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(50)))
                .andExpect(jsonPath("$[1].height", is(100)));
    }

    @Test
    public void testFindAllBedsByType() throws Exception {
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setType(BedType.KING);
        FurnitureBed bed2 = new FurnitureBed();
        bed2.setType(BedType.KING);
        when(furnitureBedService.findAllBedsByType(BedType.KING)).thenReturn(List.of(bed1, bed2));

        this.mockMvc.perform(get("/beds/type/KING")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type", is(BedType.KING.toString())))
                .andExpect(jsonPath("$[1].type", is(BedType.KING.toString())));
    }

    @Test
    public void testFindAllBedsByTypeBadRequest() throws Exception {
        this.mockMvc.perform(get("/beds/type/WRONG")).andExpect(status().isBadRequest());
    }

    @Test
    public void testFindAllBedsByWood() throws Exception{
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setWood(WoodType.MAPLE);
        FurnitureBed bed2 = new FurnitureBed();
        bed2.setWood(WoodType.MAPLE);
        when(furnitureBedService.findAllBedsByWood(WoodType.MAPLE)).thenReturn(List.of(bed1, bed2));

        this.mockMvc.perform(get("/beds/wood/maple")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].wood", is(WoodType.MAPLE.toString())))
                .andExpect(jsonPath("$[1].wood", is(WoodType.MAPLE.toString())));
    }

    @Test
    public void testFindAllBedsByWoodBadRequest() throws Exception{
        this.mockMvc.perform(get("/beds/wood/wrong")).andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBed() throws Exception{
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        ObjectMapper objectMapper = new ObjectMapper();
        String bed1Json = objectMapper.writeValueAsString(bed1);

        when(furnitureBedService.saveBed(any(FurnitureBed.class))).thenReturn(bed1);

        this.mockMvc.perform(post("/beds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bed1Json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.height", is(bed1.getHeight())))
                .andExpect(jsonPath("$.length", is(bed1.getLength())))
                .andExpect(jsonPath("$.width", is(bed1.getWidth())))
                .andExpect(jsonPath("$.type", is(bed1.getType().toString())))
                .andExpect(jsonPath("$.wood", is(bed1.getWood().toString())));

    }
}
