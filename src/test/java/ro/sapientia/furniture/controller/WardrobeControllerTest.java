package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.sapientia.furniture.model.Wardrobe;
import ro.sapientia.furniture.service.WardrobeService;

@WebMvcTest(controllers = WardrobeController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class WardrobeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WardrobeService wardrobeService;

	@Autowired
	private ObjectMapper objectMapper;

    @Test
    public void testGetAllWardrobes() throws Exception {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setId(1L);
        wardrobe.setWidth(100);
        wardrobe.setHeigth(200);
        wardrobe.setDepth(60);
        wardrobe.setNumberOfDoors(3);
        wardrobe.setHasMirror(true);

        when(wardrobeService.findAllWardrobe()).thenReturn(List.of(wardrobe));

        this.mockMvc.perform(get("/wardrobe/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].width", is(100)))
                .andExpect(jsonPath("$[0].heigth", is(200)))
                .andExpect(jsonPath("$[0].depth", is(60)))
                .andExpect(jsonPath("$[0].numberOfDoors", is(3)))
                .andExpect(jsonPath("$[0].hasMirror", is(true)));
    }

    @Test
    public void testGetWardrobeById() throws Exception {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setId(1L);
        wardrobe.setWidth(100);

        when(wardrobeService.findWardrobeById(1L)).thenReturn(wardrobe);

        this.mockMvc.perform(get("/wardrobe/find/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.width", is(100)));
    }

	@Test
	public void testAddWardrobe() throws Exception {
		Wardrobe wardrobe = new Wardrobe();
		wardrobe.setWidth(100);
		wardrobe.setHeigth(200);

		Wardrobe savedWardrobe = new Wardrobe();
		savedWardrobe.setId(1L);
		savedWardrobe.setWidth(100);
		savedWardrobe.setHeigth(200);

		when(wardrobeService.create(any(Wardrobe.class))).thenReturn(savedWardrobe);
		
		this.mockMvc
				.perform(post("/wardrobe/add").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(wardrobe)))
				.andReturn().getResponse().getContentAsString();

        this.mockMvc.perform(post("/wardrobe/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wardrobe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.width", is(100)))
                .andExpect(jsonPath("$.heigth", is(200)));
	}

    @Test
    public void testUpdateWardrobe() throws Exception {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setId(1L);
        wardrobe.setWidth(120);

        Wardrobe updatedWardrobe = new Wardrobe();
        updatedWardrobe.setId(1L);
        updatedWardrobe.setWidth(120);

		when(wardrobeService.update(any(Wardrobe.class))).thenReturn(updatedWardrobe);

        this.mockMvc.perform(post("/wardrobe/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wardrobe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.width", is(120)));
    }

    @Test
    public void testDeleteWardrobe() throws Exception {
        this.mockMvc.perform(get("/wardrobe/delete/1"))
                .andExpect(status().isOk());
    }
}
