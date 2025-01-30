package ro.sapientia.furniture.bdt.ee.definition.nightstand;


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
//import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.util.EnumConverter;

//import java.util.List;
import java.util.Map;

//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class NightstandStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    private Nightstand nightstandToCreate;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    @Given("^that we have the following nightstands:$")
    public void that_we_have_the_following_nightstands(final DataTable nightstands) throws Throwable {
        for (final Map<String, String> data : nightstands.asMaps(String.class, String.class)) {
            Nightstand nightstand = new Nightstand();
            nightstand.setWidth(Integer.parseInt(data.get("width")));
            nightstand.setHeight(Integer.parseInt(data.get("height")));
            nightstand.setDepth(Integer.parseInt(data.get("depth")));
            nightstand.setNumberOfDrawers(Integer.parseInt(data.get("numberOfDrawers")));
            nightstand.setHasLamp(Boolean.parseBoolean(data.get("hasLamp")));
            nightstand.setColor(EnumConverter.toNightstandColor(data.get("color").trim().toUpperCase()));

            this.entityManager.persist(nightstand);
        }

        this.entityManager.flush();
    }

    @When("^I invoke the nightstand all endpoint$")
    public void I_invoke_the_nightstand_all_endpoint() throws Throwable {
    }

    @Then("^I should get the color \"([^\"]*)\" for the last nightstand in the result.$")
    public void I_should_get_result_in_stories_list(final String color) throws Throwable {
//        List<Nightstand> result = this.webClient.get()
//                                                .uri("/nightstands/all")
//                                                .accept(MediaType.APPLICATION_JSON)
//                                                .retrieve()
//                                                .bodyToMono(new ParameterizedTypeReference<List<Nightstand>>() {})
//                                                .block();
//
//        assertNotNull(result);
//        assertNotEquals(0, result.size());
//        assertEquals(color.trim().toUpperCase(), result.get(result.size() - 1).getColor().toString());
        this.webClient.get()
                      .uri("/nightstands/all")
                      .accept(MediaType.APPLICATION_JSON)
                      .exchangeToMono(response -> response.toEntityList(Nightstand.class))
                      .flatMapIterable(entity -> entity.getBody())
                      .last()
                      .doOnNext(body -> {
                          assertNotNull(body);
                          assertEquals(color.trim().toUpperCase(), body.getColor().toString());
                      });
    }

    @Given("^that we have the following nightstand creation data:$")
    public void that_we_have_the_following_nightstand_creation_data(final DataTable nightstandData) throws Throwable {
        Map<String, String> data = nightstandData.asMaps(String.class, String.class).get(0);

        this.nightstandToCreate = new Nightstand();
        this.nightstandToCreate.setWidth(Integer.parseInt(data.get("width")));
        this.nightstandToCreate.setHeight(Integer.parseInt(data.get("height")));
        this.nightstandToCreate.setDepth(Integer.parseInt(data.get("depth")));
        this.nightstandToCreate.setNumberOfDrawers(Integer.parseInt(data.get("numberOfDrawers")));
        this.nightstandToCreate.setHasLamp(Boolean.parseBoolean(data.get("hasLamp")));
        this.nightstandToCreate.setColor(EnumConverter.toNightstandColor(data.get("color").trim().toUpperCase()));
    }

    @When("^I create the nightstand via the API$")
    public void I_create_the_nightstand_via_the_API() throws Throwable {
    }

    @Then("^I should get the color \"([^\"]*)\" as response.$")
    public void I_should_get_the_color_as_response(final String color) {
        Nightstand createdNightstand = this.webClient.post()
                                                     .uri("/nightstands/add")
                                                     .accept(MediaType.APPLICATION_JSON)
                                                     .bodyValue(this.nightstandToCreate)
                                                     .retrieve()
                                                     .bodyToMono(Nightstand.class)
                                                     .block();

        assertNotNull(createdNightstand);
        assertEquals(color.trim().toUpperCase(), createdNightstand.getColor().toString());
    }

    @Then("^I should get a bad request error response.$")
    public void I_should_get_a_bad_request_error_response() throws Exception {
        Nightstand createdNightstand = null;
        try {
            createdNightstand = this.webClient.post()
                                              .uri("/nightstands/add")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .retrieve()
                                              .bodyToMono(Nightstand.class)
                                              .block();
        } catch (WebClientResponseException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }

        assertNull(createdNightstand);
    }

    @After
    public void close() {
    }

}
