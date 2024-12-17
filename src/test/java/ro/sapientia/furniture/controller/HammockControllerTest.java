package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.HammockBody;
import ro.sapientia.furniture.service.HammockBodyService;
import org.springframework.http.MediaType;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = HammockController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class HammockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(HammockBodyService.class)
    private HammockBodyService hammockBodyService;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        final HammockBody hammockBody = new HammockBody();
        hammockBody.setLength(250);
        when(hammockBodyService.findAllHammockBodies()).thenReturn(List.of(hammockBody));

        this.mockMvc.perform(get("/hammock/all")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].length", is(250.0)));
    }
}
