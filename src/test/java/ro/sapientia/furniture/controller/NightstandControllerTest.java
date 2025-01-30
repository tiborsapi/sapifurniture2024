package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;
import ro.sapientia.furniture.service.NightstandService;

import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = NightstandController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class NightstandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NightstandService nightstandService;

    @Test
    public void getAllNightstandsTest() throws Exception {
        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();

        nightstand1.setColor(NightstandColor.BEIGE);
        nightstand2.setColor(NightstandColor.BLACK);
        nightstand3.setColor(NightstandColor.WHITE);

        when(this.nightstandService.findAllNightstands()).thenReturn(List.of(nightstand1, nightstand2, nightstand3));

        this.mockMvc.perform(get("/nightstands/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(3)))
                    .andExpect(jsonPath("$[0].id", is(nightstand1.getId())))
                    .andExpect(jsonPath("$[0].width", is(nightstand1.getWidth())))
                    .andExpect(jsonPath("$[0].height", is(nightstand1.getHeight())))
                    .andExpect(jsonPath("$[0].depth", is(nightstand1.getDepth())))
                    .andExpect(jsonPath("$[0].numberOfDrawers", is(nightstand1.getNumberOfDrawers())))
                    .andExpect(jsonPath("$[0].hasLamp", is(nightstand1.isHasLamp())))
                    .andExpect(jsonPath("$[0].color", is(nightstand1.getColor().toString())))
                    .andExpect(jsonPath("$[1].id", is(nightstand2.getId())))
                    .andExpect(jsonPath("$[1].width", is(nightstand2.getWidth())))
                    .andExpect(jsonPath("$[1].height", is(nightstand2.getHeight())))
                    .andExpect(jsonPath("$[1].depth", is(nightstand2.getDepth())))
                    .andExpect(jsonPath("$[1].numberOfDrawers", is(nightstand2.getNumberOfDrawers())))
                    .andExpect(jsonPath("$[1].hasLamp", is(nightstand2.isHasLamp())))
                    .andExpect(jsonPath("$[1].color", is(nightstand2.getColor().toString())))
                    .andExpect(jsonPath("$[2].id", is(nightstand3.getId())))
                    .andExpect(jsonPath("$[2].width", is(nightstand3.getWidth())))
                    .andExpect(jsonPath("$[2].height", is(nightstand3.getHeight())))
                    .andExpect(jsonPath("$[2].depth", is(nightstand3.getDepth())))
                    .andExpect(jsonPath("$[2].numberOfDrawers", is(nightstand3.getNumberOfDrawers())))
                    .andExpect(jsonPath("$[2].hasLamp", is(nightstand3.isHasLamp())))
                    .andExpect(jsonPath("$[2].color", is(nightstand3.getColor().toString())));

    }

    @Test
    public void getAllNightstandsExpectEmptyTest() throws Exception {
        when(this.nightstandService.findAllNightstands()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/nightstands/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));

    }

    @Test
    public void getAllNightstandsExpectNullTest() throws Exception {
        when(this.nightstandService.findAllNightstands()).thenReturn(null);

        this.mockMvc.perform(get("/nightstands/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
    }

    @Test
    public void getNightstandByIdTest() throws Exception {
        Long idToFind = 1L;

        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        when(this.nightstandService.findNightstandById(idToFind)).thenReturn(nightstand);

        this.mockMvc.perform(get("/nightstands/find/id/" + idToFind))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(nightstand.getId())))
                    .andExpect(jsonPath("$.width", is(nightstand.getWidth())))
                    .andExpect(jsonPath("$.height", is(nightstand.getHeight())))
                    .andExpect(jsonPath("$.depth", is(nightstand.getDepth())))
                    .andExpect(jsonPath("$.numberOfDrawers", is(nightstand.getNumberOfDrawers())))
                    .andExpect(jsonPath("$.hasLamp", is(nightstand.isHasLamp())))
                    .andExpect(jsonPath("$.color", is(nightstand.getColor().toString())));
    }

    @Test
    public void getNightstandByIdExpectNullTest() throws Exception {
        Long nonExistingId = -1L;
        when(this.nightstandService.findNightstandById(nonExistingId)).thenReturn(null);

        this.mockMvc.perform(get("/nightstands/find/id/" + nonExistingId))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
    }

    @Test
    public void getNightstandsByColorTest() throws Exception {
        NightstandColor findByColor = NightstandColor.BLACK;
        NightstandColor color2 = NightstandColor.WHITE;

        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();
        Nightstand nightstand4 = new Nightstand();
        Nightstand nightstand5 = new Nightstand();

        nightstand1.setColor(findByColor);
        nightstand2.setColor(findByColor);
        nightstand3.setColor(findByColor);
        nightstand4.setColor(color2);
        nightstand5.setColor(color2);

        when(this.nightstandService.findNightstandsByColor(findByColor)).thenReturn(List.of(nightstand1, nightstand2, nightstand3));

        this.mockMvc.perform(get("/nightstands/find/color/" + findByColor))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(3)))
                    .andExpect(jsonPath("$[0].id", is(nightstand1.getId())))
                    .andExpect(jsonPath("$[0].width", is(nightstand1.getWidth())))
                    .andExpect(jsonPath("$[0].height", is(nightstand1.getHeight())))
                    .andExpect(jsonPath("$[0].depth", is(nightstand1.getDepth())))
                    .andExpect(jsonPath("$[0].numberOfDrawers", is(nightstand1.getNumberOfDrawers())))
                    .andExpect(jsonPath("$[0].hasLamp", is(nightstand1.isHasLamp())))
                    .andExpect(jsonPath("$[0].color", is(nightstand1.getColor().toString())))
                    .andExpect(jsonPath("$[1].id", is(nightstand2.getId())))
                    .andExpect(jsonPath("$[1].width", is(nightstand2.getWidth())))
                    .andExpect(jsonPath("$[1].height", is(nightstand2.getHeight())))
                    .andExpect(jsonPath("$[1].depth", is(nightstand2.getDepth())))
                    .andExpect(jsonPath("$[1].numberOfDrawers", is(nightstand2.getNumberOfDrawers())))
                    .andExpect(jsonPath("$[1].hasLamp", is(nightstand2.isHasLamp())))
                    .andExpect(jsonPath("$[1].color", is(nightstand2.getColor().toString())))
                    .andExpect(jsonPath("$[2].id", is(nightstand3.getId())))
                    .andExpect(jsonPath("$[2].width", is(nightstand3.getWidth())))
                    .andExpect(jsonPath("$[2].height", is(nightstand3.getHeight())))
                    .andExpect(jsonPath("$[2].depth", is(nightstand3.getDepth())))
                    .andExpect(jsonPath("$[2].numberOfDrawers", is(nightstand3.getNumberOfDrawers())))
                    .andExpect(jsonPath("$[2].hasLamp", is(nightstand3.isHasLamp())))
                    .andExpect(jsonPath("$[2].color", is(nightstand3.getColor().toString())));
    }

    @Test
    public void getNightstandsByColorExpectEmptyTest() throws Exception {
        when(this.nightstandService.findNightstandsByColor(any())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/nightstands/find/color/gray"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void getNightstandsByColorExpectNullTest() throws Exception {
        when(this.nightstandService.findNightstandsByColor(any())).thenReturn(null);

        this.mockMvc.perform(get("/nightstands/find/color/gray"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
    }

    @Test
    public void addNightstandTest() throws Exception {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        ObjectMapper objectMapper = new ObjectMapper();
        String nightstandJson = objectMapper.writeValueAsString(nightstand);

        when(this.nightstandService.create(nightstand)).thenReturn(nightstand);

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightstandJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(nightstand.getId())))
                    .andExpect(jsonPath("$.width", is(nightstand.getWidth())))
                    .andExpect(jsonPath("$.height", is(nightstand.getHeight())))
                    .andExpect(jsonPath("$.depth", is(nightstand.getDepth())))
                    .andExpect(jsonPath("$.numberOfDrawers", is(nightstand.getNumberOfDrawers())))
                    .andExpect(jsonPath("$.hasLamp", is(nightstand.isHasLamp())))
                    .andExpect(jsonPath("$.color", is(nightstand.getColor().toString())));
    }

    @Test
    public void addNightstandExpectNullTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String nightstandJson = objectMapper.writeValueAsString(new Nightstand());

        when(this.nightstandService.create(any())).thenReturn(null);

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightstandJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(""));
    }

    @Test
    public void addNightstandExpectDataIntegrityViolationTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String nightstandJson = objectMapper.writeValueAsString(new Nightstand());

        when(this.nightstandService.create(any())).thenThrow(DataIntegrityViolationException.class);

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightstandJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(""));
    }

    @Test
    public void deleteNightstandByIdTest() throws Exception {
        Long idToDelete = 1L;
        doNothing().when(this.nightstandService).delete(any());

        this.mockMvc.perform(delete("/nightstands/delete/" + idToDelete))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));

        verify(this.nightstandService, times(1)).delete(idToDelete);
    }

    @Test
    public void deleteNightstandByIdExpectNotFoundTest() throws Exception {
        Long nonExistingId = -1L;
        doThrow(new EmptyResultDataAccessException(1)).when(this.nightstandService).delete(nonExistingId);

        this.mockMvc.perform(delete("/nightstands/delete/" + nonExistingId))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));

        verify(this.nightstandService, times(1)).delete(nonExistingId);
    }

}
