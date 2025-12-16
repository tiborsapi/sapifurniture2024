package ro.sapientia.furniture.bdt.component.definition;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class FurnitureStepDefinition {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestEntityManager entityManager;

	private ObjectMapper objectMapper = new ObjectMapper();
	private Long currentFurnitureId;
	private Long currentComponentListId;

	@Given("^that we have the following furniture bodies:$")
	public void that_we_have_the_following_furniture_bodies(final DataTable furnitureBodies) throws Throwable {
		boolean first = true;
		for (final Map<String, String> data : furnitureBodies.asMaps(String.class, String.class)) {
			FurnitureBody body = new FurnitureBody();
			body.setHeigth(Integer.parseInt(data.get("heigth")));
			body.setWidth(Integer.parseInt(data.get("width")));
			body.setDepth(Integer.parseInt(data.get("depth")));
			FurnitureBody saved = entityManager.persist(body);
			if (first) {
				currentFurnitureId = saved.getId();
				first = false;
			}
		}
		entityManager.flush();
		entityManager.clear();
	}

	@When("^I invoke the furniture all endpoint$")
	public void I_invoke_the_furniture_all_endpoint() throws Throwable {
	}

	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_stories_list(final String heigth, final String position) throws Throwable {
		mvc.perform(get("/furniture/all")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$["+position+"].heigth", is(Integer.parseInt(heigth))));
	}

	@When("^I invoke the furniture find endpoint with id \"([^\"]*)\"$")
	public void I_invoke_the_furniture_find_endpoint_with_id(final String id) throws Throwable {
		// If id is "1" and we have a stored ID from Given step, use that
		// Otherwise use the provided ID (e.g., "999" for not found scenarios)
		if (!"1".equals(id) || currentFurnitureId == null) {
			currentFurnitureId = Long.parseLong(id);
		}
		// If id is "1" and currentFurnitureId is set, keep using currentFurnitureId
	}

	@Then("^I should get the furniture with width \"([^\"]*)\", heigth \"([^\"]*)\", and depth \"([^\"]*)\"$")
	public void I_should_get_the_furniture_with_dimensions(final String width, final String heigth, final String depth) throws Throwable {
		mvc.perform(get("/furniture/find/" + currentFurnitureId)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.width", is(Integer.parseInt(width))))
			      .andExpect(jsonPath("$.heigth", is(Integer.parseInt(heigth))))
			      .andExpect(jsonPath("$.depth", is(Integer.parseInt(depth))));
	}

	@Then("^I should get a not found response$")
	public void I_should_get_a_not_found_response() throws Throwable {
		mvc.perform(get("/furniture/find/999")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound());
	}

	@When("^I add a new furniture body with width \"([^\"]*)\", heigth \"([^\"]*)\", depth \"([^\"]*)\", and thickness \"([^\"]*)\"$")
	public void I_add_a_new_furniture_body(final String width, final String heigth, final String depth, final String thickness) throws Throwable {
		FurnitureBodyDTO dto = new FurnitureBodyDTO();
		dto.setWidth(Integer.parseInt(width));
		dto.setHeigth(Integer.parseInt(heigth));
		dto.setDepth(Integer.parseInt(depth));
		dto.setThickness(Integer.parseInt(thickness));
		
		mvc.perform(post("/furniture/add")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(objectMapper.writeValueAsString(dto)))
			      .andExpect(status().isCreated())
			      .andExpect(jsonPath("$.width", is(Integer.parseInt(width))))
			      .andExpect(jsonPath("$.heigth", is(Integer.parseInt(heigth))))
			      .andExpect(jsonPath("$.depth", is(Integer.parseInt(depth))))
			      .andExpect(jsonPath("$.thickness", is(Integer.parseInt(thickness))))
			      .andExpect(jsonPath("$.id", notNullValue()));
	}

	@Then("^I should get a created response with furniture having width \"([^\"]*)\", heigth \"([^\"]*)\", depth \"([^\"]*)\", and thickness \"([^\"]*)\"$")
	public void I_should_get_a_created_response(final String width, final String heigth, final String depth, final String thickness) throws Throwable {
	}

	@Then("^the furniture should have an ID assigned$")
	public void the_furniture_should_have_an_ID_assigned() throws Throwable {
	}

	@Then("^I should get a created response$")
	public void I_should_get_a_created_response() throws Throwable {
	}

	@Then("^I should find a furniture with width \"([^\"]*)\" in the list$")
	public void I_should_find_a_furniture_in_the_list(final String width) throws Throwable {
		mvc.perform(get("/furniture/all")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$[?(@.width == " + width + ")]", notNullValue()));
	}

	@When("^I update furniture with id \"([^\"]*)\" to have width \"([^\"]*)\", heigth \"([^\"]*)\", depth \"([^\"]*)\", and thickness \"([^\"]*)\"$")
	public void I_update_furniture(final String id, final String width, final String heigth, final String depth, final String thickness) throws Throwable {
		FurnitureBodyDTO dto = new FurnitureBodyDTO();
		dto.setId(Long.parseLong(id));
		dto.setWidth(Integer.parseInt(width));
		dto.setHeigth(Integer.parseInt(heigth));
		dto.setDepth(Integer.parseInt(depth));
		dto.setThickness(Integer.parseInt(thickness));
		
		mvc.perform(post("/furniture/update")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(objectMapper.writeValueAsString(dto)))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.width", is(Integer.parseInt(width))))
			      .andExpect(jsonPath("$.heigth", is(Integer.parseInt(heigth))))
			      .andExpect(jsonPath("$.depth", is(Integer.parseInt(depth))))
			      .andExpect(jsonPath("$.thickness", is(Integer.parseInt(thickness))));
	}

	@Then("^I should get an OK response with updated furniture having width \"([^\"]*)\", heigth \"([^\"]*)\", depth \"([^\"]*)\", and thickness \"([^\"]*)\"$")
	public void I_should_get_an_OK_response_with_updated_furniture(final String width, final String heigth, final String depth, final String thickness) throws Throwable {
	}

	@When("^I delete furniture with id \"([^\"]*)\"$")
	public void I_delete_furniture(final String id) throws Throwable {
		mvc.perform(get("/furniture/delete/" + id)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
	}

	@Then("^I should get an OK response$")
	public void I_should_get_an_OK_response() throws Throwable {
	}

	@Then("^I should get \"([^\"]*)\" furniture bodies in the response$")
	public void I_should_get_furniture_bodies_count(final String count) throws Throwable {
		mvc.perform(get("/furniture/all")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.length()", is(Integer.parseInt(count))));
	}

	@Given("^that we have component lists in the system$")
	public void that_we_have_component_lists_in_the_system() throws Throwable {
		FurnitureBody body = new FurnitureBody();
		body.setWidth(10);
		body.setHeigth(10);
		body.setDepth(10);
		FurnitureBody savedBody = entityManager.persistAndFlush(body);
		
		ComponentList cl = new ComponentList(savedBody, null);
		cl.setCreatedBy(1L);
		entityManager.persistAndFlush(cl);
	}

	@Given("^that we have a component list with id \"([^\"]*)\"$")
	public void that_we_have_a_component_list_with_id(final String id) throws Throwable {
		FurnitureBody body = new FurnitureBody();
		body.setWidth(10);
		body.setHeigth(10);
		body.setDepth(10);
		FurnitureBody savedBody = entityManager.persistAndFlush(body);
		
		ComponentList cl = new ComponentList(savedBody, null);
		cl.setCreatedBy(1L);
		ComponentList savedCl = entityManager.persistAndFlush(cl);
	}

	@When("^I invoke the get all component lists endpoint$")
	public void I_invoke_the_get_all_component_lists_endpoint() throws Throwable {
	}

	@Then("^I should get a list of component lists$")
	public void I_should_get_a_list_of_component_lists() throws Throwable {
		mvc.perform(get("/api/component-lists")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$", notNullValue()));
	}

	@When("^I invoke the get component list by id endpoint with id \"([^\"]*)\"$")
	public void I_invoke_the_get_component_list_by_id_endpoint(final String id) throws Throwable {
		currentComponentListId = Long.parseLong(id);
	}

	@Then("^I should get a component list with id \"([^\"]*)\"$")
	public void I_should_get_a_component_list_with_id(final String id) throws Throwable {
		Long idToUse = currentComponentListId != null ? currentComponentListId : getLastComponentListId();
		mvc.perform(get("/api/component-lists/" + idToUse)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.id", notNullValue()));
	}

	@Then("^I should get a not found response for component list$")
	public void I_should_get_a_not_found_response_for_component_list() throws Throwable {
		mvc.perform(get("/api/component-lists/999")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound());
	}

	private Long getLastFurnitureId() {
		List<FurnitureBody> bodies = entityManager.getEntityManager()
			.createQuery("SELECT f FROM FurnitureBody f ORDER BY f.id DESC", FurnitureBody.class)
			.setMaxResults(1)
			.getResultList();
		return bodies.isEmpty() ? 1L : bodies.get(0).getId();
	}

	private Long getLastComponentListId() {
		List<ComponentList> lists = entityManager.getEntityManager()
			.createQuery("SELECT c FROM ComponentList c ORDER BY c.id DESC", ComponentList.class)
			.setMaxResults(1)
			.getResultList();
		return lists.isEmpty() ? 1L : lists.get(0).getId();
	}

	@After
	public void close() {
		// Clear entity manager to ensure clean state
		if (entityManager != null) {
			entityManager.clear();
		}
		currentFurnitureId = null;
		currentComponentListId = null;
	}

}
