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

import ro.sapientia.furniture.model.WoodenTrunk;
import ro.sapientia.furniture.service.WoodenTrunkService;

@WebMvcTest(controllers = WoodenTrunkController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class WoodenTrunkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WoodenTrunkService woodenTrunkService;

    @Test
    public void shouldReturnAllWoodenTrunks() throws Exception {
        // Given: WoodenTrunk mock object
        final WoodenTrunk trunk = new WoodenTrunk();
        trunk.setHeight(100);
        trunk.setWidth(50);
        trunk.setDepth(40);
        trunk.setMaterial("oak");
        trunk.setHasLid(true);
        trunk.setCapacity(200);

        // When: Mocking the service response
        when(woodenTrunkService.findAllWoodenTrunks()).thenReturn(List.of(trunk));

        // Then: Perform the GET request and validate the response
        this.mockMvc.perform(get("/wooden-trunk/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(100)))
                .andExpect(jsonPath("$[0].width", is(50)))
                .andExpect(jsonPath("$[0].depth", is(40)))
                .andExpect(jsonPath("$[0].material", is("oak")))
                .andExpect(jsonPath("$[0].hasLid", is(true)))
                .andExpect(jsonPath("$[0].capacity", is(200)));
    }
}