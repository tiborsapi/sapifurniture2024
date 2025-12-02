package ro.sapientia.furniture.bdt.ee.definition;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class FurnitureStepDefinition {

	@Autowired
	private TestEntityManager entityManager;

	@Given("^that we have the following furniture bodies:$")
	public void that_we_have_the_following_furniture_bodies(final DataTable furnitureBodies) throws Throwable {
		for (final Map<String, String> data : furnitureBodies.asMaps(String.class, String.class)) {
			FurnitureBodyDTO body = new FurnitureBodyDTO();
			body.setHeigth(Integer.parseInt(data.get("heigth")));
			body.setWidth(Integer.parseInt(data.get("width")));
			body.setDepth(Integer.parseInt(data.get("depth")));
			entityManager.persist(body);
		}
		entityManager.flush();

	}

	@When("^I invoke the furniture all endpoint$")
	public void I_invoke_the_furniture_all_endpoint() throws Throwable {
	}

	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_stories_list(final String heigth, final String position) throws Throwable {
		WebClient webClient = WebClient.create();
		webClient.get().uri("/furniture/all") // The endpoint being tested
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToMono(response -> response.toEntityList(FurnitureBodyDTO.class)) // Converts the response to a
																						// list
				.flatMapIterable(entity -> entity.getBody()) // Works with each body item
				.elementAt(0) // Access the element at 'position'
				.doOnNext(fb -> {
					assert fb != null;
					assert fb.getHeigth() == 20;
				});
	}

	@After
	public void close() {
	}

}
