package ro.sapientia.door.componentTest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.model.Door;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
public class DoorApplicationComponentTests {
	@Autowired
	private MockMvc mvc;
	  
	@Autowired
	private TestEntityManager entityManager;
	private void addOneElement() {
		Door door = new Door();
	    door.setHeigth(200);
	    entityManager.persist(door);
	    entityManager.flush();
	}
	@Test
	void furnitureAll_oneElement() throws Exception{
		addOneElement();
		mvc.perform(get("/door/all")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
			    .andExpect(content()
			    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("$[0].heigth", is(200)));
	}
	    

}