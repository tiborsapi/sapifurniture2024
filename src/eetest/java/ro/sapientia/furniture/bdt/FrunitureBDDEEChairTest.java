package ro.sapientia.furniture.bdt;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
		  features = { "src/eetest/resources/ro/sapientia/furniture/bdt/ee" },
		  glue = {"ro.sapientia.furniture.chair.ee.test" },
		  publish = false,
		  dryRun = true)
public class FrunitureBDDEEChairTest {

}
