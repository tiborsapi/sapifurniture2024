package ro.sapientia.tvstand.bdt;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		  features = { "src/eetest/resources/ro.sapientia.tvstand.bdt/component" },
		  glue = {"ro.sapientia.tvstand.bdt.component.definition" },
		  publish = false,
		  dryRun = true)
public class TvStandBDDComponentTest {

}
