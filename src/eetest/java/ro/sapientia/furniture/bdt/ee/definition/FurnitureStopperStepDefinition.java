package ro.sapientia.furniture.bdt.ee.definition;

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

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ro.sapientia.furniture.dto.StopFurnitureRequestDTO;
import ro.sapientia.furniture.model.FurnitureStopper;

@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class FurnitureStopperStepDefinition {

	class FurnitureStopperResponse {
		@JsonProperty("furniture_body_id")
		public int furnitureBodyId;
	}

	@Autowired
	private TestEntityManager entityManager;

	@Given("^Given that we have the following furniture stoppers:$")
	public void given_that_we_have_the_following_furniture_stoppers(final DataTable furnitureStoppers)
			throws Throwable {
		for (final Map<String, String> data : furnitureStoppers.asMaps(String.class, String.class)) {
			FurnitureStopper stopper = new FurnitureStopper();
			stopper.setId(Long.parseLong(data.get("id")));
			stopper.setStartTimeMs(Integer.parseInt(data.get("startTimeMs")));
			stopper.setStopTimeMs(Integer.parseInt(data.get("stopTimeMs")));
			stopper.setActive(Boolean.parseBoolean(data.get("active")));
			entityManager.persist(stopper);
		}

		entityManager.flush();
	}

	@When("^I stop the furniture body with id \"([^\"]*)\" with furniture stopper id \"([^\"]*)\"$")
	public void I_stop_the_furniture_body_with_id_with_furniture_stopper_id(final String bodyId, final String stopperId)
			throws Throwable {
		WebClient webClient = WebClient.create();
		webClient.post().uri("/furniture-stopper")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new StopFurnitureRequestDTO(Long.parseLong(bodyId), Long.parseLong(stopperId)))
				.retrieve()
				.bodyToMono(FurnitureStopper.class)
				.doOnNext(stopper -> {
					assert stopper.getId() == Long.parseLong(stopperId);
				});
	}

	@Then("^the furniture stopper with id \"([^\"]*)\" should have stopped the furniture with id \"([^\"]*)\"$")
	public void the_furniture_stopper_with_id_should_have_stopped_the_furniture_with_id(final String stopperId,
			final String bodyId) throws Throwable {
		WebClient webClient = WebClient.create();
		webClient.get().uri("/furniture-stopper/find/" + stopperId)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(FurnitureStopperResponse.class)
				.doOnNext(stopper -> {
					assert stopper.furnitureBodyId == Integer.parseInt(bodyId);
				});
	}

	@Then("^the database should show furniture stopper with id \"([^\"]*)\" as active$")
	public void the_database_should_show_furniture_stopper_with_id_as_active(final String stopperId) throws Throwable {
		FurnitureStopper stopper = entityManager.find(FurnitureStopper.class, Long.parseLong(stopperId));
		assert stopper != null;
		assert stopper.isActive();
	}

	@After
	public void close() {
	}

}
