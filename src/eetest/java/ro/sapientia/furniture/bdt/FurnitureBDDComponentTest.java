package ro.sapientia.furniture.bdt;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		  features = { "src/eetest/resources/ro/sapientia/furniture/bdt/component" },
		  glue = {"ro.sapientia.furniture.bdt.component.definition" },
		  publish = false,
		  dryRun = false)
public class FurnitureBDDComponentTest {

}
