package ro.sapientia.wardrobe;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
          features = { "src/eetest/resources/ro/sapientia/wardrobe/ee" },
          glue = {"ro.sapientia.wardrobe.ee.definition" },
          publish = false,
          dryRun = true)
public class WardrobeBDDEETest {

}