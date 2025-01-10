package ro.sapientia.furniture.bdt;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = { "src/eetest/resources/ro/sapientia/furniture/bdt/component/WoodenTrunk" },
    glue = { "ro.sapientia.furniture.bdt.component.definition.woodentrunk" },
    plugin = {
        "pretty",
        "json:target/cucumber-reports/cucumber-component.json",
        "html:target/cucumber-reports/component-html-report"
    },
    publish = false,
    dryRun = false
)
public class WoodenTrunkBDDComponentTest {

}