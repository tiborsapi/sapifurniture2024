package ro.sapientia.furniture.bdt.component.definition;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import ro.sapientia.furniture.dto.ManufacturedComponentDTO;
import ro.sapientia.furniture.model.*;
import ro.sapientia.furniture.repository.*;
import ro.sapientia.furniture.service.ManufacturedComponentService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:cotest.properties")
public class ManufacturedComponentStepDefinition {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ManufacturedComponentTypeRepository typeRepository;

    @Autowired
    private ComponentListRepository componentListRepository;

    @Autowired
    private ManufacturedComponentService service;

    @Given("^that we have the following manufactured components:$")
    public void that_we_have_the_following_manufactured_components(DataTable components) {

        ComponentList list = new ComponentList();
        list.setCreatedBy(1L);
        list.setCreatedAt(java.time.LocalDateTime.now());
        list.setUpdatedAt(java.time.LocalDateTime.now());
        componentListRepository.save(list);

        for (Map<String, String> row : components.asMaps()) {
            ManufacturedComponentType type =
                typeRepository.findByName(row.get("type"))
                    .orElseGet(() -> {
                        ManufacturedComponentType t = new ManufacturedComponentType();
                        t.setName(row.get("type"));
                        return typeRepository.save(t);
                    });

            ManufacturedComponentDTO dto = new ManufacturedComponentDTO();
            dto.setComponentListId(list.getId());
            dto.setManufacturedComponentTypeId(type.getId());
            dto.setQuantity(Integer.parseInt(row.get("quantity")));

            service.create(dto);
        }
    }

    @When("^I invoke the manufactured components all endpoint$")
    public void i_invoke_the_manufactured_components_all_endpoint() {}

    @Then("^I should see a manufactured component with type \"([^\"]*)\"$")
    public void i_should_see_a_manufactured_component_with_type(String type) throws Exception {

        mvc.perform(get("/api/manufactured-components")
                .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[*].manufacturedComponentType.name", hasItem(type)));
    }
}
