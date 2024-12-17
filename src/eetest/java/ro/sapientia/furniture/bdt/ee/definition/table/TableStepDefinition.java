package ro.sapientia.furniture.bdt.ee.definition.table;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ro.sapientia.furniture.dto.TableCreationDTO;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.util.EnumConverter;

import java.util.ArrayList;
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
public class TableStepDefinition {

    @Autowired
    private TestEntityManager entityManager;

    private TableCreationDTO creationDTO;

    private WebClient.ResponseSpec creationResponse;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();


    @Given("^that we have the following furniture tables:$")
    public void that_we_have_the_following_furniture_tables(final DataTable furnitureTables) throws Throwable {
        for (final Map<String, String> data : furnitureTables.asMaps(String.class, String.class)) {
            FurnitureTable table = new FurnitureTable();
            table.setHeight(Integer.parseInt(data.get("height")));
            table.setWidth(Integer.parseInt(data.get("width")));
            table.setDepth(Integer.parseInt(data.get("depth")));
            table.setColor(EnumConverter.tableColorEnumConverter(data.get("color")));
            table.setType(EnumConverter.tableTypeEnumConverter(data.get("type")));

            entityManager.persist(table);
        }
        entityManager.flush();
    }

    @When("^I invoke the table all endpoint$")
    public void I_invoke_the_table_all_endpoint() throws Throwable {
    }

    @Then("^I should get the color \"([^\"]*)\" for the last furniture table in the result.$")
    public void I_should_get_result_in_stories_list(final String color) throws Throwable {

        List<FurnitureTable> result = webClient.get()
                .uri("/tables")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FurnitureTable>>() {})
                .block();

        assert result != null;

        assert !result.isEmpty();

        assert result.get(result.size() - 1).getColor().toString().equals(color);
    }

    @Given("^that I have the following table creation data:$")
    public void that_I_have_the_following_table_data(final DataTable tableData) {
        Map<String, String> data = tableData.asMaps(String.class, String.class).get(0);

        creationDTO = new TableCreationDTO();
        creationDTO.setHeight(Integer.parseInt(data.get("height")));
        creationDTO.setWidth(Integer.parseInt(data.get("width")));
        creationDTO.setDepth(Integer.parseInt(data.get("depth")));
        creationDTO.setColor(data.get("color"));
        creationDTO.setType(data.get("type"));
    }

    @When("^I create the table via the API$")
    public void I_create_the_table_via_the_api(){
        this.creationResponse = webClient.post()
                .uri("/tables")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(creationDTO)
                .retrieve();
    }

    @Then("I should get the color {string} and type {string} as response.")
    public void I_should_get_the_color_and_type_as_response(final String color, final String type) {
        FurnitureTable createdTable = creationResponse.bodyToMono(FurnitureTable.class).block();
        assert createdTable != null;
        assert createdTable.getColor().toString().equals(color.trim());
        assert createdTable.getType().toString().equals(type.trim());
    }

    @Then("I should get a bad request error response")
    public void I_should_get_bad_request() throws Exception {

        FurnitureTable createdTable;
        try{
             createdTable = creationResponse.bodyToMono(FurnitureTable.class).block();

        }catch (WebClientResponseException e){
            assert e.getStatusCode().equals(HttpStatus.BAD_REQUEST);

            return;
        }
        assert createdTable == null;
    }

    @After
    public void close() {
    }
}
