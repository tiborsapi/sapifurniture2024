package ro.sapientia.furniture.bdt.component.definition.hammock;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import io.cucumber.java.en.And;
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
import ro.sapientia.furniture.model.HammockBody;

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
public class HammockStepDefinition {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    @Given("^that we have the following hammocks$")
    public void that_we_have_the_following_hammocks(final DataTable hammocks) throws Throwable {
        for (final Map<String, String> hammock : hammocks.asMaps(String.class, String.class)) {
            HammockBody hammockBody = new HammockBody();
            hammockBody.setLength(Double.parseDouble(hammock.get("length")));
            hammockBody.setWidth(Double.parseDouble(hammock.get("width")));
            hammockBody.setMaterial(hammock.get("material"));
            hammockBody.setWeight(Double.parseDouble(hammock.get("weight")));
            entityManager.persist(hammockBody);
        }
        entityManager.flush();
    }

    @When("^I invoke the hammock all endpoint$")
    public void I_invoke_the_hammock_all_endpoint() throws Throwable {
        mockMvc.perform(get("/hammock/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Then("^I should get the length \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
    public void I_should_get_the_length_for_the_position(final String length, final String position) throws Throwable {
        mockMvc.perform(get("/hammock/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[" + position + "].length", is(Double.parseDouble(length))));
    }

    @After
    public void cleanup() {
        entityManager.getEntityManager().createQuery("delete from HammockBody").executeUpdate();
        entityManager.flush();
    }

    @Given("the furniture database is empty")
    public void theFurnitureDatabaseIsEmpty() {
        
    }

    @Given("that the following hammock bodies exist in the database:")
    public void thatTheFollowingHammockBodiesExistInTheDatabase() {
        
    }

    @When("I invoke the {string} endpoint")
    public void iInvokeTheEndpoint(String arg0) {
        
    }

    @Then("I should receive a response with status code {int}")
    public void iShouldReceiveAResponseWithStatusCode(int arg0) {
        
    }

    @And("I should get a hammock body with the following details at position {string}:")
    public void iShouldGetAHammockBodyWithTheFollowingDetailsAtPosition(String arg0) {
    }
}
