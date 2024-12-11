package ro.sapientia.cupboard.bdt.ee.definition;

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
import ro.sapientia.furniture.model.Cupboard;

@CucumberContextConfiguration
@SpringBootTest(classes = ro.sapientia.furniture.FurnitureApplication.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class CupboardStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following cupboard bodies:$")
    public void that_we_have_the_following_cupboard_bodies(final DataTable cupboardBodies) throws Throwable {
        for (final Map<String, String> data : cupboardBodies.asMaps(String.class, String.class)) {
            Cupboard body = new Cupboard();
            body.setHeight(Integer.parseInt(data.get("height")));
            body.setWidth(Integer.parseInt(data.get("width")));
            body.setNumberOfDrawer(Integer.parseInt(data.get("numberOfDrawer")));
            body.setNumberOfCab(Integer.parseInt(data.get("numberOfCab")));
            entityManager.persist(body);
        }
        entityManager.flush();
    }

    @When("^I invoke the cupboard all endpoint$")
    public void I_invoke_the_cupboard_all_endpoint() throws Throwable {
    }

    @Then("^I should get the height \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
    public void I_should_get_result_in_stories_list(final String height, final String position) throws Throwable {
        WebClient webClient = WebClient.create();
        webClient.get().uri("/cupboard/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.toEntityList(Cupboard.class))
                .flatMapIterable(entity -> entity.getBody())
                .elementAt(0)
                .doOnNext(cb -> {
                    assert cb != null;
                    assert cb.getHeight() == Integer.parseInt(height);
                });
    }

    @After
    public void close() {
    }
}