package ro.sapientia.furniture.bdt;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/eetest/resources/ro/sapientia/furniture/bdt/ee/nightstand" },
        glue = {"ro.sapientia.furniture.bdt.ee.definition.nightstand" },
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/html-report.html"
        },
        publish = false,
        dryRun = false)
public class NightstandBDDEETest {

}
