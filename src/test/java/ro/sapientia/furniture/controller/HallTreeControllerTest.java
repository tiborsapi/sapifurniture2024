package ro.sapientia.furniture.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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

import ro.sapientia.furniture.exception.CannotHangClothingException;
import ro.sapientia.furniture.exception.CannotTakeOffClothingException;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.HallTreeBody;
import ro.sapientia.furniture.service.HallTreeBodyService;

@WebMvcTest(controllers = HallTreeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class HallTreeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean(HallTreeBodyService.class)
	private HallTreeBodyService hallTreeBodyService;
	
	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final HallTreeBody body = new HallTreeBody();
		body.setHeigth(10);
		when(hallTreeBodyService.findAllHallTreeBodies()).thenReturn(List.of(body));

		this.mockMvc.perform(get("/hallTree/all")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].heigth", is(10)));
	}
	
	@Test
	public void findByIdShouldReturnMessageFromService() throws Exception {
		final HallTreeBody body = new HallTreeBody();
		body.setId(1L);
		body.setHeigth(10);
		when(hallTreeBodyService.findHallTreeBodyById(1L)).thenReturn(body);

		this.mockMvc.perform(get("/hallTree/find/1")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.heigth", is(10)))
				.andExpect(jsonPath("$.id", is(1)));
		}
	
	@Test
	public void hangClothingShouldReturnErrorMessageFromService() throws Exception {
	    final HallTreeBody body = new HallTreeBody();
	    body.setId(1L);
	    body.setHangers(10);
	    body.setHangedClothes(10);

	    when(hallTreeBodyService.findHallTreeBodyById(1L)).thenReturn(body);

	    doThrow(new CannotHangClothingException("Cannot hang clothing: no more space."))
	            .when(hallTreeBodyService).hangClothing(body);

	    this.mockMvc.perform(post("/hallTree/hangClothing/{id}", 1L))
	            .andExpect(status().isBadRequest())
	            .andExpect(content().string("Cannot hang clothing: no more space."));
	}
	
	@Test
	public void takeOffClothingShouldReturnErrorMessageFromService() throws Exception{
		final HallTreeBody body = new HallTreeBody();
	    body.setId(1L);
	    body.setHangers(10);
	    body.setHangedClothes(0);

	    when(hallTreeBodyService.findHallTreeBodyById(1L)).thenReturn(body);
	    
	    doThrow(new CannotTakeOffClothingException("Cannot take off clothing: no clothes are hung."))
	    	.when(hallTreeBodyService).takeOffClothing(body);
	    
	    this.mockMvc.perform(post("/hallTree/takeOffClothing/{id}", 1L))
	            .andExpect(status().isBadRequest())
	            .andExpect(content().string("Cannot take off clothing: no clothes are hung."));
	}

	@Test
	public void createHallTreeBodyShouldReturnCreatedBody() throws Exception{
		final HallTreeBody body = new HallTreeBody();
		body.setHeigth(15);
		body.setWidth(20);
		body.setDepth(10);
		
		when(hallTreeBodyService.create(any(HallTreeBody.class))).thenReturn(body);
		
		this.mockMvc.perform(post("/hallTree/add")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"heigth\": 15, \"width\": 20, \"depth\": 10}"))
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.heigth", is(15)))
            .andExpect(jsonPath("$.width", is(20)))
            .andExpect(jsonPath("$.depth", is(10)));
	}
	
	@Test
	public void updateHallTreeBodyShouldReturnUpdatedBody() throws Exception {
	    final HallTreeBody updatedBody = new HallTreeBody();
	    updatedBody.setId(1L);
	    updatedBody.setHeigth(15);
	    updatedBody.setWidth(20);
	    updatedBody.setDepth(10);

	    when(hallTreeBodyService.update(any(HallTreeBody.class))).thenReturn(updatedBody);

	    this.mockMvc.perform(post("/hallTree/update")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"id\": 1, \"heigth\": 15, \"width\": 20, \"depth\": 10}"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.heigth", is(15)))
	            .andExpect(jsonPath("$.width", is(20)))
	            .andExpect(jsonPath("$.depth", is(10)))
	            .andExpect(jsonPath("$.id", is(1)));
	}

	
	@Test
    public void deleteHallTreeBodyShouldReturnNoContent() throws Exception {
        Long id = 1L;
       
        this.mockMvc.perform(get("/hallTree/delete/{id}", id))
                .andExpect(status().isOk());
    }
	
	
}
