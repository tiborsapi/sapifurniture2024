package ro.sapientia.furniture.bdt.ee.definition.WineRack;


import io.cucumber.datatable.DataTable;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FurnitureWineRack;

import java.util.List;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class FurnitureWineRackStepDefinition {
    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following furniture wineracks:$")
    public void that_we_have_the_following_furniture_wineracks(final DataTable furnitureWineRacks) throws Throwable {
        for (final Map<String, String> data : furnitureWineRacks.asMaps(String.class, String.class)) {
            FurnitureWineRack wineRack = new FurnitureWineRack();
            wineRack.setCapacity(Integer.parseInt(data.get("capacity")));
            wineRack.setCurrentLoad(Integer.parseInt(data.get("currentLoad")));
            wineRack.setTransparentFront(Boolean.parseBoolean(data.get("transparentFront")));
            entityManager.persist(wineRack);
        }
        entityManager.flush();

    }

    @When("^I invoke the winerack all endpoint$")
    public void I_invoke_the_furniture_all_endpoint() throws Throwable {
    }

    @Then("^I should get the transparentFront \"([^\"]*)\"")
    public void I_should_get_result_in_stories_list(final String transparentFront) throws Throwable {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        List<FurnitureWineRack> result = webClient.get()
                .uri("/winerack")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FurnitureWineRack>>() {})
                .block();

        assert result != null;

        assert !result.isEmpty();

        assert result.get(result.size() - 1).isTransparentFront() == Boolean.parseBoolean(transparentFront);
    }

}
