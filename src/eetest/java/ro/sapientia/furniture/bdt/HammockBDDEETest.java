package ro.sapientia.furniture.bdt;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/eetest/resources/ro/sapientia/furniture/bdt/ee/hammock" },
        glue = {"ro.sapientia.furniture.bdt.ee.definition.hammock" },
        publish = false,
        dryRun = false // TODO: fix running with dryRun false
)
public class HammockBDDEETest {
}
