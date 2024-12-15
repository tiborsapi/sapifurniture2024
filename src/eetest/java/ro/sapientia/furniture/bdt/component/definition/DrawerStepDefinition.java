package ro.sapientia.furniture.bdt.component.definition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.sapientia.furniture.model.Drawer;
import ro.sapientia.furniture.service.DrawerService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@SpringBootTest
public class DrawerStepDefinition {

    @Autowired
    private DrawerService drawerService;

    private Drawer newDrawer;
    private List<Drawer> openDrawers;

    @Given("I have drawer details")
    public void i_have_drawer_details(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> drawerData = rows.get(0);

        newDrawer = Drawer.builder()
                .material(drawerData.get("material"))
                .color(drawerData.get("color"))
                .height(Double.parseDouble(drawerData.get("height")))
                .width(Double.parseDouble(drawerData.get("width")))
                .depth(Double.parseDouble(drawerData.get("depth")))
                .weight(Double.parseDouble(drawerData.get("weight")))
                .maxOpenDistance(Double.parseDouble(drawerData.get("maxOpenDistance")))
                .weightCapacity(Double.parseDouble(drawerData.get("weightCapacity")))
                .build();
    }

    @When("I create a new drawer")
    public void i_create_a_new_drawer() {
        newDrawer = drawerService.createDrawer(newDrawer);
    }

    @Then("the drawer should be saved successfully")
    public void the_drawer_should_be_saved_successfully() {
        assertThat(newDrawer.getId()).isNotNull();
    }

    @Then("the drawer should have status {string}")
    public void the_drawer_should_have_status(String status) {
        assertThat(newDrawer.getStatus().name()).isEqualTo(status);
    }

    @Given("there are some drawers in the system")
    public void there_are_some_drawers_in_the_system() {
        // Setup test data
        Drawer drawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .height(15.0)
                .width(50.0)
                .depth(45.0)
                .weight(5.0)
                .isOpen(true)
                .maxOpenDistance(40.0)
                .currentOpenDistance(10.0)
                .weightCapacity(25.0)
                .status(Drawer.DrawerStatus.FUNCTIONAL)
                .build();
        drawerService.createDrawer(drawer);
    }

    @When("I request all open drawers")
    public void i_request_all_open_drawers() {
        openDrawers = drawerService.findOpenDrawers();
    }

    @Then("I should receive only open drawers")
    public void i_should_receive_only_open_drawers() {
        assertThat(openDrawers).isNotEmpty();
        assertThat(openDrawers).allMatch(Drawer::isOpen);
    }
}