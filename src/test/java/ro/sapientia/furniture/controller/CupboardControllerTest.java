package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.Cupboard;
import ro.sapientia.furniture.service.CupboardService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CupboardController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class CupboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(CupboardService.class)
    private CupboardService cupboardService;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        final Cupboard cupboard = new Cupboard();
        cupboard.setHeight(500);
        when(cupboardService.findAllCupboard()).thenReturn(List.of(cupboard));

        this.mockMvc.perform(get("/cupboard/all")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(500)));
    }
}
