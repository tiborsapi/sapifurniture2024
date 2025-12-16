package ro.sapientia.furniture.bdt.component.definition;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialTypeRepository;
import ro.sapientia.furniture.service.RawMaterialService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:cotest.properties")
@ContextConfiguration
public class RawMaterialStepDefinition {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    @Autowired
    private RawMaterialService rawMaterialService;

    @Given("^that we have the following raw materials:$")
    public void that_we_have_the_following_raw_materials(final DataTable rawMaterials) throws Throwable {
        for (final Map<String, String> data : rawMaterials.asMaps(String.class, String.class)) {
            // Find or create RawMaterialType by unique name
            String typeName = data.get("type");
            RawMaterialType type = rawMaterialTypeRepository.findByName(typeName)
                    .orElseGet(() -> {
                        RawMaterialType t = new RawMaterialType();
                        t.setName(typeName);
                        return rawMaterialTypeRepository.save(t);
                    });

            RawMaterial rm = new RawMaterial();
            rm.setHeight(Integer.parseInt(data.get("height")));
            rm.setWidth(Integer.parseInt(data.get("width")));
            rm.setLength(Integer.parseInt(data.get("length")));
            rm.setRawMaterialType(type);
            rm.setQuantity(Integer.parseInt(data.get("quantity")));
            rawMaterialService.findOrCreateAndIncreaseQuantity(rm);
        }
        entityManager.flush();
    }

    @When("^I invoke the raw materials all endpoint$")
    public void i_invoke_the_raw_materials_all_endpoint() throws Throwable {
        // no-op here; the assertion step will perform the GET
    }

    @Then("^I should get the height \"([^\"]*)\" for the position \\\"([^\\\"]*)\\\"$")
    public void i_should_get_height_for_position(final String height, final String position) throws Throwable {
        mvc.perform(get("/raw-materials/all")
                  .contentType(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$[" + position + "].height", is(Integer.parseInt(height))));
    }

    @After
	public void close() {
	}
}