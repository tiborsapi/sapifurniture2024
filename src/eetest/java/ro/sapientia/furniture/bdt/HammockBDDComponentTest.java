package ro.sapientia.furniture.bdt;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/eetest/resources/ro/sapientia/furniture/bdt/component/hammock" },
        glue = {"ro.sapientia.furniture.bdt.component.definition.hammock" },
    publish = false,
    dryRun = true
)
public class HammockBDDComponentTest {
}
