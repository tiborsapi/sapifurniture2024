package ro.sapientia.furniture.bdt.ee.definition;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.model.BabyCotBody;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class BabyCotStepDefinition {

	@Autowired
	private TestEntityManager entityManager;

	@Given("^that we have the following baby cot bodies:$")
	public void that_we_have_the_following_baby_cot_bodies(final DataTable babyCotBodies) throws Throwable {
		for (final Map<String, String> data : babyCotBodies.asMaps(String.class, String.class)) {
			BabyCotBody body = new BabyCotBody();
			body.setHeigth(Integer.parseInt(data.get("heigth")));
			body.setWidth(Integer.parseInt(data.get("width")));
			body.setDepth(Integer.parseInt(data.get("depth")));
			body.setDepth(Integer.parseInt(data.get("maxAge")));
			body.setDepth(Integer.parseInt(data.get("rating")));
			entityManager.persist(body);
		}
		entityManager.flush();

	}

	@When("^I invoke the baby cot all endpoint$")
	public void I_invoke_the_baby_cot_all_endpoint() throws Throwable {
	}

	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_stories_list(final String heigth, final int position) throws Throwable {
		WebClient webClient = WebClient.create();
    	webClient.get().uri("/babycot/all")
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(response -> response.toEntityList(BabyCotBody.class))
        .flatMapIterable(entity -> entity.getBody())
        .elementAt(position)
        .doOnNext(wb -> {
            assert wb != null;
            assert wb.getHeigth() == Integer.parseInt(heigth);
        });
	}

	@After
	public void close() {
	}

}
