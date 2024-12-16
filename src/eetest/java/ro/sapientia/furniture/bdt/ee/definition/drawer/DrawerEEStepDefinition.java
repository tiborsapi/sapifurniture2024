package ro.sapientia.furniture.bdt.ee.definition.drawer;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ro.sapientia.furniture.model.Drawer;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DrawerEEStepDefinition {

    @Autowired
    private TestRestTemplate restTemplate;

    private Drawer drawerRequest;
    private ResponseEntity<Drawer> singleDrawerResponse;
    private ResponseEntity<List<Drawer>> multiDrawerResponse;
    private List<Drawer> setupDrawers;

    @Given("I have a drawer with the following specifications")
    public void i_have_a_drawer_with_specifications(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> drawerData = rows.get(0);

        drawerRequest = Drawer.builder()
                .material(drawerData.get("material"))
                .color(drawerData.get("color"))
                .height(Double.parseDouble(drawerData.get("height")))
                .width(Double.parseDouble(drawerData.get("width")))
                .depth(Double.parseDouble(drawerData.get("depth")))
                .weight(Double.parseDouble(drawerData.get("weight")))
                .maxOpenDistance(Double.parseDouble(drawerData.get("maxOpenDistance")))
                .weightCapacity(Double.parseDouble(drawerData.get("weightCapacity")))
                .isOpen(Boolean.parseBoolean(drawerData.get("isOpen")))
                .build();
    }

    @When("I create the drawer through the API")
    public void i_create_the_drawer_through_api() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Drawer> request = new HttpEntity<>(drawerRequest, headers);

        singleDrawerResponse = restTemplate.postForEntity("/api/drawers", request, Drawer.class);
    }

    @Then("the API response status should be {int}")
    public void the_api_response_status_should_be(int expectedStatus) {
        HttpStatus actualStatus = singleDrawerResponse != null
                ? singleDrawerResponse.getStatusCode()
                : multiDrawerResponse.getStatusCode();
        assertThat(actualStatus.value()).isEqualTo(expectedStatus);
    }

    @Then("the returned drawer should match the specifications")
    public void the_returned_drawer_should_match_specifications() {
        Drawer responseDrawer = singleDrawerResponse.getBody();
        assertThat(responseDrawer).isNotNull();
        assertThat(responseDrawer.getId()).isNotNull();
        assertThat(responseDrawer.getMaterial()).isEqualTo(drawerRequest.getMaterial());
        assertThat(responseDrawer.getColor()).isEqualTo(drawerRequest.getColor());
        assertThat(responseDrawer.getHeight()).isEqualTo(drawerRequest.getHeight());
        assertThat(responseDrawer.getWidth()).isEqualTo(drawerRequest.getWidth());
        assertThat(responseDrawer.getDepth()).isEqualTo(drawerRequest.getDepth());
        assertThat(responseDrawer.getWeight()).isEqualTo(drawerRequest.getWeight());
        assertThat(responseDrawer.getMaxOpenDistance()).isEqualTo(drawerRequest.getMaxOpenDistance());
        assertThat(responseDrawer.getWeightCapacity()).isEqualTo(drawerRequest.getWeightCapacity());
        assertThat(responseDrawer.isOpen()).isEqualTo(drawerRequest.isOpen());
    }

    @Given("there are existing drawers in the system with states")
    public void there_are_existing_drawers_with_states(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        setupDrawers = rows.stream().map(drawerData -> {
            Drawer drawer = Drawer.builder()
                    .material(drawerData.get("material"))
                    .color(drawerData.get("color"))
                    .height(Double.parseDouble(drawerData.get("height")))
                    .width(Double.parseDouble(drawerData.get("width")))
                    .depth(Double.parseDouble(drawerData.get("depth")))
                    .weight(Double.parseDouble(drawerData.get("weight")))
                    .maxOpenDistance(Double.parseDouble(drawerData.get("maxOpenDistance")))
                    .weightCapacity(Double.parseDouble(drawerData.get("weightCapacity")))
                    .isOpen(Boolean.parseBoolean(drawerData.get("isOpen")))
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Drawer> request = new HttpEntity<>(drawer, headers);
            ResponseEntity<Drawer> response = restTemplate.postForEntity("/api/drawers", request, Drawer.class);
            return response.getBody();
        }).toList();
    }

    @When("I request all open drawers through the API")
    public void i_request_all_open_drawers_through_api() {
        multiDrawerResponse = restTemplate.exchange(
                "/api/drawers/open",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }

    @Then("I should receive only the open drawers")
    public void i_should_receive_only_open_drawers() {
        List<Drawer> drawers = multiDrawerResponse.getBody();
        assertThat(drawers).isNotNull();
        assertThat(drawers).allMatch(Drawer::isOpen);
        assertThat(drawers.size()).isEqualTo(
                setupDrawers.stream().filter(Drawer::isOpen).count()
        );
    }
}