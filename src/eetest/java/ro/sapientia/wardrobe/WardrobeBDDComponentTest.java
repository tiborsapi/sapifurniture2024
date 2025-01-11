package ro.sapientia.wardrobe;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
          features = { "src/eetest/resources/ro/sapientia/wardrobe/component" },
          glue = {"ro.sapientia.wardrobe.component.definition" },
          plugin = {
                    "pretty",
                    "json:target/cucumber-reports/cucumber-component.json",
                    "html:target/cucumber-reports/html-component-report"
                },
          publish = false,
          dryRun = false)
public class WardrobeBDDComponentTest {

}