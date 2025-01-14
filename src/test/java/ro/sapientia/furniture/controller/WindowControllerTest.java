package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import ro.sapientia.furniture.model.Window;
import ro.sapientia.furniture.service.WindowService;

@WebMvcTest(controllers = WindowController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class WindowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WindowService windowService;

    @Test
    public void shouldReturnWindowDetails() throws Exception {
        // Create a mock Window object
        final Window window = new Window();
        window.setHeight(100);
        window.setWidth(50);
        window.setGlassType("Tempered");

        // Mock the service to return the window object
        when(windowService.findAllWindows()).thenReturn(List.of(window));

        // Perform GET request and validate the response
        this.mockMvc.perform(get("/window/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(100)))
                .andExpect(jsonPath("$[0].width", is(50)))
                .andExpect(jsonPath("$[0].glassType", is("Tempered")));
    }
}
