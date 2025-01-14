package ro.sapientia.wardrobe.ee.definition;

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
import ro.sapientia.furniture.FurnitureApplication;
import ro.sapientia.furniture.model.Wardrobe;

@CucumberContextConfiguration
@SpringBootTest(classes = FurnitureApplication.class)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class WardrobeStepDefinition {

    @Autowired
    private TestEntityManager entityManager;
    
    @Given("^that we have the following wardrobes:$")
    public void that_we_have_the_following_wardrobes(final DataTable wardrobes) throws Throwable {
        for (final Map<String, String> data : wardrobes.asMaps(String.class, String.class)) {
            Wardrobe wardrobe = new Wardrobe();
            wardrobe.setHeigth(Integer.parseInt(data.get("heigth")));
            wardrobe.setWidth(Integer.parseInt(data.get("width")));
            wardrobe.setDepth(Integer.parseInt(data.get("depth")));
            wardrobe.setNumberOfDoors(Integer.parseInt(data.get("number_of_doors")));
            wardrobe.setHasMirror(Boolean.parseBoolean(data.get("has_mirror")));
            wardrobe.setNumberOfShelves(Integer.parseInt(data.get("number_of_shelves")));
            entityManager.persist(wardrobe);
        }
        entityManager.flush();
    }

    @When("^I invoke the wardrobe all endpoint$")
    public void I_invoke_the_wardrobe_all_endpoint() throws Throwable {
    }

    @Then("^I should get the heigth \"([^\"]*)\" for the position \"([^\"]*)\"$")
    public void I_should_get_result_in_wardrobe_list(final String heigth, final int position) throws Throwable {
    	WebClient webClient = WebClient.create();
    	webClient.get().uri("/wardrobe/all")
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(response -> response.toEntityList(Wardrobe.class))
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