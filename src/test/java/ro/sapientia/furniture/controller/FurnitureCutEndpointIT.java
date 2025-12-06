package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.model.dto.CutRequestDTO;
import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class FurnitureCutEndpointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cutEndpoint_placesTwoElements() throws Exception {
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(10); e1.setHeight(10);
        FurnitureBodyDTO e2 = new FurnitureBodyDTO(); e2.setId(2L); e2.setWidth(15); e2.setHeight(10);
        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(30); req.setSheetHeight(20);
        req.setElements(List.of(e1, e2));

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/furniture/cut")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placements.length()", is(2)))
                .andExpect(jsonPath("$.placements[0].id", is(1)))
                .andExpect(jsonPath("$.placements[1].id", is(2)));
    }

    @Test
    void cutEndpoint_throwsOnEmptyElements() throws Exception {
        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(10); req.setSheetHeight(10);
        req.setElements(List.of());
        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/furniture/cut")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());
    }
}

