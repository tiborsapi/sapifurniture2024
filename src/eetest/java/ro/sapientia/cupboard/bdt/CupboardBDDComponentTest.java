package ro.sapientia.cupboard.bdt;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/eetest/resources/ro/sapientia/cupboard/bdt/component" },
        glue = {"ro.sapientia.cupboard.bdt.component.definition" },
        publish = false,
        dryRun = true)
public class CupboardBDDComponentTest {

}