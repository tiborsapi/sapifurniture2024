package ro.sapientia.cupboard.bdt;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/eetest/resources/ro/sapientia/cupboard/bdt/ee" },
        glue = {"ro.sapientia.cupboard.bdt.ee.definition" },
        publish = false,
        dryRun = true)
public class CupboardBDDEETest {

}
