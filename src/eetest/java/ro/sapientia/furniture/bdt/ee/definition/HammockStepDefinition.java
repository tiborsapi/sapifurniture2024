package ro.sapientia.furniture.bdt.ee.definition;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.HammockBody;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class HammockStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following hammocks$")
    public void that_we_have_the_following_hammocks(final DataTable hammocks) throws Throwable {
        for (final Map<String, String> data : hammocks.asMaps(String.class, String.class)) {
            HammockBody hammockBody = new HammockBody();
            hammockBody.setLength(Integer.parseInt(data.get("length")));
            hammockBody.setWidth(Integer.parseInt(data.get("width")));
            hammockBody.setMaterial(data.get("material"));
            hammockBody.setWeight(Integer.parseInt(data.get("weight")));
            entityManager.persist(hammockBody);
        }
        entityManager.flush();
    }

    @When("^I invoke the hammock all endpoint$")
    public void I_invoke_the_hammock_all_endpoint() throws Throwable {

    }

    @Then("^I should get the length \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
    public void I_should_get_the_length_for_the_position(final String length, final String position) throws Throwable {
        WebClient webClient = WebClient.create();
        webClient.get().uri("/hammock/all") // The endpoint being tested
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.toEntityList(HammockBody.class)) // Converts the response to a
                                                                                       // list
                .flatMapIterable(HttpEntity::getBody) // Works with each body item
                .elementAt(0) // Access the element at 'position'
                .doOnNext(fb -> {
                    assert fb != null;
                    assert fb.getLength() == 20;
                });
    }

    @After
    public void cleanup() {
        entityManager.clear();
        entityManager.flush();
    }
}
