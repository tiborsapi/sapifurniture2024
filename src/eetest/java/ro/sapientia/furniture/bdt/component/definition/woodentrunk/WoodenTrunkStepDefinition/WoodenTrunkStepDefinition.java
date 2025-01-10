package ro.sapientia.furniture.bdt.component.definition.woodentrunk.WoodenTrunkStepDefinition;

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

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.model.WoodenTrunk;

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
public class WoodenTrunkStepDefinition {

    @Autowired
    private MockMvc mvc;

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
        mvc.perform(get("/wooden-trunk/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[" + position + "].height", is(Integer.parseInt(height))));
    }

    @After
    public void close() {
    }
}