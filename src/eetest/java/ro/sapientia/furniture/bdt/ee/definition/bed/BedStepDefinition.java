package ro.sapientia.furniture.bdt.ee.definition.bed;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.util.EnumConverter;

import java.util.Map;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class BedStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following bed bodies:$")
    public void that_we_have_the_following_bed_bodies(final DataTable bedBodies) throws Throwable {
        for (final Map<String, String> data : bedBodies.asMaps(String.class, String.class)) {
            FurnitureBed bed = new FurnitureBed();
            bed.setHeight(Integer.parseInt(data.get("height")));
            bed.setWidth(Integer.parseInt(data.get("width")));
            bed.setLength(Integer.parseInt(data.get("length")));
            bed.setWood(EnumConverter.woodTypeEnumConverter(data.get("wood")));
            bed.setType(EnumConverter.bedTypeEnumConverter(data.get("type")));
            entityManager.persist(bed);
        }
        entityManager.flush();

    }

    @When("^I invoke the bed all endpoint$")
    public void I_invoke_the_bed_all_endpoint() throws Throwable {
    }

    @Then("^I should get the wood \"([^\"]*)\" for the bed in position \\\"([^\\\"]*)\\\"$")
    public void i_should_get_the_wood_for_the_bed_in_position(final String wood, final String index) throws Throwable {
        WebClient webClient = WebClient.create();
        webClient.get().uri("/beds/all") // The endpoint being tested
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.toEntityList(FurnitureBed.class))
                .flatMapIterable(entity -> entity.getBody()) // Works with each body item
                .elementAt(Integer.parseInt(index)) // Access the element at 'position'
                .doOnNext(bed -> {
                    assert bed != null;
                    assert Objects.equals(bed.getWood().toString(), wood);
                });
    }

    @After
    public void close() {
    }
}
