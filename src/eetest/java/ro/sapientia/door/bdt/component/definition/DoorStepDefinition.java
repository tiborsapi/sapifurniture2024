package ro.sapientia.door.bdt.component.definition;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.FurnitureApplication;
import ro.sapientia.furniture.model.Door;
import ro.sapientia.furniture.model.FurnitureBody;

@CucumberContextConfiguration
@SpringBootTest(classes = FurnitureApplication.class)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class DoorStepDefinition {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestEntityManager entityManager;

	@Given("^that we have the following doors:$")
	public void that_we_have_the_following_doors(final DataTable doors) throws Throwable {
		for (final Map<String, String> data : doors.asMaps(String.class, String.class)) {
			Door door = new Door();
			door.setHeigth(Integer.parseInt(data.get("heigth")));
			door.setWidth(Integer.parseInt(data.get("width")));
			door.setDepth(Integer.parseInt(data.get("depth")));
			door.setColor(data.get("color"));
			door.setMaterial(data.get("material"));
			door.setHasGlass(Boolean.parseBoolean(data.get("hasGlass")));
			door.setNumberOfGlassPanels(Integer.parseInt(data.get("numberOfGlassPanels")));
			entityManager.persist(door); //az adatbazisba menti 
		}
		entityManager.flush();

	}
	@When("^I invoke the door all endpoint$")
	public void I_invoke_the_door_all_endpoint() throws Throwable {
	}
	
	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_door_list(final String heigth, final String position) throws Throwable {
		mvc.perform(get("/door/all")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$["+position+"].heigth", is(Integer.parseInt(heigth))));
	}

	@After
	public void close() {
	}

}
