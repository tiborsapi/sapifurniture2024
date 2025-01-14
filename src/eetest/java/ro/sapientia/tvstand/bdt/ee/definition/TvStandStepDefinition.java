package ro.sapientia.tvstand.bdt.ee.definition;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;

import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(classes = ro.sapientia.furniture.FurnitureApplication.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class TvStandStepDefinition {

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
		WebClient webClient = WebClient.create();
		webClient.get().uri("/tvstand/all") // The endpoint being tested
				.accept(MediaType.APPLICATION_JSON)
				.exchangeToMono(response -> response.toEntityList(Tvstand.class)) // Converts the response to a
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
