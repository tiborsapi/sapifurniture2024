package ro.sapientia.wardrobe.component.definition;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import ro.sapientia.furniture.FurnitureApplication;
import ro.sapientia.furniture.model.Wardrobe;

@SpringBootTest(classes = FurnitureApplication.class)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@CucumberContextConfiguration
@ContextConfiguration
public class WardrobeComponentStepDefinition {

    @Autowired
    private MockMvc mvc;

    @Given("^the database is initialized with wardrobes$")
    public void the_database_is_initialized_with_wardrobes() throws Throwable {
        Wardrobe wardrobe1 = new Wardrobe();
        wardrobe1.setWidth(100);
        wardrobe1.setHeigth(200);
        wardrobe1.setDepth(60);
        wardrobe1.setNumberOfDoors(3);
        wardrobe1.setHasMirror(true);
        wardrobe1.setNumberOfShelves(4);
        
        Wardrobe wardrobe2 = new Wardrobe();
        wardrobe2.setWidth(120);
        wardrobe2.setHeigth(250);
        wardrobe2.setDepth(70);
        wardrobe2.setNumberOfDoors(4);
        wardrobe2.setHasMirror(false);
        wardrobe2.setNumberOfShelves(5);
        	
        mvc.perform(post("/wardrobe/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"width\":100,\"heigth\":200,\"depth\":60,\"numberOfDoors\":3,\"hasMirror\":true,\"numberOfShelves\":4}"))
                .andExpect(status().isCreated());
        
        mvc.perform(post("/wardrobe/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"width\":120,\"heigth\":250,\"depth\":70,\"numberOfDoors\":4,\"hasMirror\":false,\"numberOfShelves\":5}"))
                .andExpect(status().isCreated());
    }

    @When("^a GET request is made to \"([^\"]*)\"$")
    public void a_GET_request_is_made_to(String endpoint) throws Throwable {
    }

    @Then("^the response status should be (\\d+)$")
    public void the_response_status_should_be(int statusCode) throws Throwable {
        mvc.perform(get("/wardrobe/all"))
            .andExpect(status().is(statusCode));
    }

    @Then("^the response should contain wardrobes$")
    public void the_response_should_contain_wardrobes() throws Throwable {
        mvc.perform(get("/wardrobe/all")
                  .contentType(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$[0].heigth", is(200)))
                  .andExpect(jsonPath("$[1].heigth", is(250)));
    }

}