package ro.sapientia.furniture.bdt.ee.definition;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.sapientia.furniture.model.RockingChairModel;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@CucumberContextConfiguration
@SpringBootTest
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
@ContextConfiguration
public class RockingChairStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    private ResponseEntity<List<RockingChairModel>> response;

    @Given("^that we have the following rocking chairs:$")
    public void that_we_have_the_following_rocking_chairs(final DataTable rockingChairs) throws Throwable {
        for (final Map<String, String> data : rockingChairs.asMaps(String.class, String.class)) {
            RockingChairModel chair = new RockingChairModel();
            chair.setMaterial(data.get("material"));
            chair.setDepth(Integer.parseInt(data.getOrDefault("depth", "0")));
            chair.setHeight(Integer.parseInt(data.getOrDefault("height", "0")));
            chair.setRockerRadius(Double.parseDouble(data.getOrDefault("rockerRadius", "0.0")));
            chair.setWidth(Integer.parseInt(data.getOrDefault("width", "0")));
            chair.setSeatHeight(Integer.parseInt(data.getOrDefault("seatHeight", "0")));
            entityManager.persist(chair);
        }
        entityManager.flush();
    }


//    @When("^I invoke the rocking chairs all endpoint$")
//    public void I_invoke_the_rocking_chairs_all_endpoint() throws Throwable {
//        WebClient webClient = WebClient.create();
//        Mono<ResponseEntity<List<RockingChairModel>>> responseMono =
//                webClient.get()
//                        .uri("/rocking-chairs") // Replace with your endpoint URL
//                        .accept(MediaType.APPLICATION_JSON)
//                        .exchangeToMono(response -> response.toEntity(List.of(RockingChairModel.class)));
//
//        this.response = responseMono.block();
//    }

    @Then("^I should get the material \"([^\"]*)\" for the position \"(\\d+)\"$")
    public void I_should_get_material_for_the_position(final String material, final int position) throws Throwable {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).get(position).getMaterial()).isEqualTo(material);
    }

    @After
    public void close() {
    }
}