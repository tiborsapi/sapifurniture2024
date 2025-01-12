package ro.sapientia.furniture.bdt.ee.definition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
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
import ro.sapientia.furniture.model.Bookshelf;
import ro.sapientia.furniture.util.Category;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class BookshelfStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    private WebClient webClient;
    private List<Map> jsonResponse;

    public BookshelfStepDefinition() {
        this.webClient = WebClient.create("http://localhost:8080");
    }

    @Given("^that the following bookshelves exist:$")
    public void that_the_following_bookshelves_exist(final DataTable bookshelves) throws Throwable {
        for (final Map<String, String> data : bookshelves.asMaps(String.class, String.class)) {
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setCapacity(Integer.parseInt(data.get("capacity")));
            bookshelf.setCategory(Category.valueOf(data.get("category")));
            entityManager.persist(bookshelf);
        }
        entityManager.flush();
    }

    @When("^I invoke the bookshelf all endpoint$")
    public void I_invoke_the_bookshelf_all_endpoint() throws Throwable {
        jsonResponse = webClient.get()
                .uri("/bookshelf/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
    }

    @Then("^I should get the capacity \"([^\"]*)\" and category \"([^\"]*)\" for the position \"([^\"]*)\"$")
    public void I_should_get_the_capacity_and_category_for_the_position(final String capacity, final String category, final String position) throws Throwable {
        int index = Integer.parseInt(position);

        Map<String, Object> bookshelf = jsonResponse.get(index);

        assertEquals(Integer.parseInt(capacity), bookshelf.get("capacity"));
        assertEquals(category, bookshelf.get("category"));
    }

    @After
    public void close() {
        // Perform cleanup operations if needed.
    }
}

