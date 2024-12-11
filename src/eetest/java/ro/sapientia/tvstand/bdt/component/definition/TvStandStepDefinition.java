package ro.sapientia.tvstand.bdt.component.definition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
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
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@CucumberContextConfiguration
@SpringBootTest(classes = ro.sapientia.furniture.FurnitureApplication.class)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class TvStandStepDefinition {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestEntityManager entityManager;

	@Given("^that we have the following tv stads:$")
	public void that_we_have_the_following_tv_stand(final DataTable tvStand) throws Throwable {
		for (final Map<String, String> data : tvStand.asMaps(String.class, String.class)) {
			Tvstand stand = new Tvstand();
			stand.setHeigth(Integer.parseInt(data.get("heigth")));
			stand.setWidth(Integer.parseInt(data.get("width")));
			stand.setDepth(Integer.parseInt(data.get("depth")));
			stand.setDoors(Integer.parseInt(data.get("door")));
			stand.setMaterial(data.get("material"));
			entityManager.persist(stand);
		}
		entityManager.flush();

	}

	@When("^I invoke the tv stand all endpoint$")
	public void I_invoke_the_furniture_all_endpoint() throws Throwable {
	}

	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_stories_list(final String heigth, final String position) throws Throwable {
		mvc.perform(get("/tvstand/all")
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
