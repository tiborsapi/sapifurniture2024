package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.mapper.FurnitureMapper;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.service.FurnitureBodyService;

@TestPropertySource(locations = "classpath:mvc.properties")
@WebMvcTest(controllers = FurnitureController.class, excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
public class FurnitureControllerTest {

        @Autowired
        private MockMvc mockMvc;

        private final ObjectMapper om = new ObjectMapper();

        @MockBean
        private FurnitureBodyService furnitureBodyService;

        @MockBean
        private FurnitureMapper furnitureMapper;

        @Test
        public void greetingShouldReturnMessageFromService() throws Exception {
                final FurnitureBody body = new FurnitureBody();
                body.setHeigth(10);
                final FurnitureBodyDTO dto = new FurnitureBodyDTO();
                dto.setHeigth(10);
                when(furnitureBodyService.findAllFurnitureBodies()).thenReturn(List.of(body));
                when(furnitureMapper.toDto(body)).thenReturn(dto);

                this.mockMvc.perform(get("/furniture/all")).andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].heigth", is(10)));
        }

        @Test
        public void getById_returnsOk() throws Exception {
                final FurnitureBody body = new FurnitureBody();
                final FurnitureBodyDTO dto = new FurnitureBodyDTO();
                when(furnitureBodyService.findFurnitureBodyById(5L)).thenReturn(body);
                when(furnitureMapper.toDto(body)).thenReturn(dto);

                this.mockMvc.perform(get("/furniture/find/5")).andExpect(status().isOk());
        }

        @Test
        public void add_update_and_delete_flows() throws Exception {
                final FurnitureBody body = new FurnitureBody();
                final FurnitureBody persistent = new FurnitureBody();
                final FurnitureBodyDTO dto = new FurnitureBodyDTO();
                final FurnitureBodyDTO respDto = new FurnitureBodyDTO();

                when(furnitureMapper.toEntity(any(FurnitureBodyDTO.class))).thenReturn(body);
                when(furnitureBodyService.create(any(FurnitureBody.class))).thenReturn(persistent);
                when(furnitureMapper.toDto(persistent)).thenReturn(respDto);

                this.mockMvc.perform(post("/furniture/add").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(dto)))
                                .andExpect(status().isCreated());

                when(furnitureBodyService.update(any(FurnitureBody.class))).thenReturn(persistent);
                when(furnitureMapper.toDto(persistent)).thenReturn(respDto);

                this.mockMvc.perform(post("/furniture/update").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(dto)))
                                .andExpect(status().isOk());

                // delete endpoint (mapped as GET)
                this.mockMvc.perform(get("/furniture/delete/7")).andExpect(status().isOk());
        }
}
