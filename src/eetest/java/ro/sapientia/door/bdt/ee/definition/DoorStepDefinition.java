package ro.sapientia.door.bdt.ee.definition;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.junit.jupiter.api.Assertions;
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
import ro.sapientia.furniture.FurnitureApplication;
import ro.sapientia.furniture.model.Door;

@CucumberContextConfiguration
@SpringBootTest(classes = FurnitureApplication.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.	NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class DoorStepDefinition {
	
	@Autowired
	private TestEntityManager entityManager;

	@Given("^that we have the following doors:$")
	public void that_we_have_the_following_doors(final DataTable doors) throws Throwable {
		for (final Map<String, String> data : doors.asMaps(String.class, String.class)) //atalakitja a tablazatot egy listava
		{
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
		entityManager.flush(); //az osszes valtozas bekeruljon az adatbazisba

	}
	@When("^I invoke the door all endpoint$")
	public void I_invoke_the_door_all_endpoint() throws Throwable {
	}

	
	@Then("^I should get the heigth \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
	public void I_should_get_result_in_door_list(final String heigth, final String position) throws Throwable {
		WebClient webClient = WebClient.create();
		webClient.get().uri("/door/all") // /door/all endpointot teszteli
				.accept(MediaType.APPLICATION_JSON) //json formaban
				.exchangeToMono(response -> response.toEntityList(Door.class)) // atalakitja Mono listava
				.flatMapIterable(entity -> entity.getBody()) // minden elemet kulon dolgoz fel
				.elementAt(0) // Access the element at 'position'
				.doOnNext(d -> {
					assert d != null;
					assert d.getHeigth() == 200;
				});
	}
	

	

	@After
	public void close() {
	}


}
