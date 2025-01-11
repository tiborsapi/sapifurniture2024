package ro.sapientia.door.btd;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		  features = { "src/eetest/resources/ro/sapientia/door/component" },
		  glue = {"ro.sapientia.door.bdt.component.definition" },
				    plugin = {
					        "pretty",
					        "json:target/cucumber-reports/cucumber.json",
					        "html:target/cucumber-reports/html-report"
					    },
					    publish = false,
					    dryRun = false)
public class DoorBDDComponentTest {

}
