package ro.sapientia.furniture.bdt.component.definition;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.model.entities.FurnitureBody;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class FurnitureStepDefinition {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Autowired
	private TestEntityManager entityManager;

	@Before
	public void setupMvc() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Given("^that we have the following furniture bodies:$")
	public void that_we_have_the_following_furniture_bodies(final DataTable furnitureBodies) {
		for (final Map<String, String> data : furnitureBodies.asMaps(String.class, String.class)) {
			FurnitureBody body = new FurnitureBody();
			body.setHeight(Integer.parseInt(data.get("heigth")));
			body.setWidth(Integer.parseInt(data.get("width")));
			body.setDepth(Integer.parseInt(data.get("depth")));
			entityManager.persist(body);
		}
		entityManager.flush();

	}

	@When("^I invoke the furniture all endpoint$")
	public void I_invoke_the_furniture_all_endpoint() {
	}

	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_stories_list(final String height, final String position) throws Exception {
		mvc.perform(get("/furniture/all")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$["+position+"].height", is(Integer.parseInt(height))));
	}

	@After
	public void close() {
	}

}
