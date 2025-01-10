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

import ro.sapientia.furniture.model.Door;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.service.DoorService;

@WebMvcTest(controllers = DoorController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class DoorControllerTest {
	
	//tobb service-t tudunk hasznalni
	//rest api end point lehet
	// itt is a mocot hasnaljuk mert nem hasznalunk rendes adatbazist
	
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean(DoorService.class)
	private DoorService furnitureBodyService;
	
	//ellenorzom, hogy a /door/all endpoint megfelelo választ ad, ajto magassaga 200 ertek lesz
	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		//letrehozok egy door peldanyt
		final Door door = new Door();
		door.setHeigth(200);
		
		when(furnitureBodyService.findAllDoors()).thenReturn(List.of(door));
		//egy HTTP GET keres /door/all endpoint-ra lekerem az ossze ajtokat s vizsgaljuk hogy ez az end point jo statust ad e vissza
		this.mockMvc.perform(get("/door/all")).andExpect(status().isOk()) // 200 status az jo
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) // json formatumban adja vissza
				.andExpect(jsonPath("$[0].heigth", is(200))); // az elso ertek az megegyezik azzal amit megadtunk neki
	}
	


}
