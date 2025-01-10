package ro.sapientia.furniture.bdt.ee.definition.woodenTrunk;

import static org.hamcrest.CoreMatchers.is;

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
import ro.sapientia.furniture.model.WoodenTrunk;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class WoodenTrunkStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following wooden trunks:$")
    public void that_we_have_the_following_wooden_trunks(final DataTable woodenTrunks) throws Throwable {
        for (final Map<String, String> data : woodenTrunks.asMaps(String.class, String.class)) {
            WoodenTrunk trunk = new WoodenTrunk();
            trunk.setHeight(Integer.parseInt(data.get("height")));
            trunk.setWidth(Integer.parseInt(data.get("width")));
            trunk.setDepth(Integer.parseInt(data.get("depth")));
            trunk.setMaterial(data.get("material"));
            trunk.setHasLid(Boolean.parseBoolean(data.get("hasLid")));
            trunk.setCapacity(Integer.parseInt(data.get("capacity")));
            entityManager.persist(trunk);
        }
        entityManager.flush();
    }

    @When("^I invoke the wooden trunk all endpoint$")
    public void i_invoke_the_wooden_trunk_all_endpoint() throws Throwable {
    }

    @Then("^I should get the height \"([^\"]*)\" for the position \"([^\"]*)\"$")
    public void i_should_get_the_height_for_the_position(final String height, final String position) throws Throwable {
        WebClient webClient = WebClient.create();
        webClient.get()
                .uri("/wooden-trunk/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(WoodenTrunk.class)
                .elementAt(Integer.parseInt(position))
                .doOnNext(trunk -> {
                    assert trunk.getHeight() == Integer.parseInt(height);
                })
                .block();
    }

    @After
    public void close() {
    }
}