package ro.sapientia.furniture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.config.SecurityConfiguration;
import ro.sapientia.furniture.model.Drawer;
import ro.sapientia.furniture.model.Drawer.DrawerStatus;
import ro.sapientia.furniture.service.DrawerService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DrawerController.class)
@Import(SecurityConfiguration.class)
class DrawerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DrawerService drawerService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getOpenDrawers_ShouldReturnDrawers() throws Exception {
        // Given
        Drawer drawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .isOpen(true)
                .build();
        when(drawerService.findOpenDrawers())
                .thenReturn(Collections.singletonList(drawer));

        // When/Then
        //log $[0].material
        mockMvc.perform(get("/api/drawers/open"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].material").value("Wood"))
                .andExpect(jsonPath("$[0].open").value(true));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createDrawer_ShouldReturnCreatedDrawer() throws Exception {
        // Given
        Drawer drawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .height(15.0)
                .width(50.0)
                .depth(45.0)
                .weight(5.0)
                .isOpen(false)
                .maxOpenDistance(40.0)
                .currentOpenDistance(0.0)
                .weightCapacity(25.0)
                .status(DrawerStatus.FUNCTIONAL)
                .description("Test drawer")
                .build();

        when(drawerService.createDrawer(any(Drawer.class)))
                .thenReturn(drawer);

        // When/Then
        mockMvc.perform(post("/api/drawers")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(drawer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.material").value("Wood"))
                .andExpect(jsonPath("$.color").value("Brown"));
    }
}