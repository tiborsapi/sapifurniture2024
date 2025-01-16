package ro.sapientia.furniture.bdt;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/eetest/resources/ro/sapientia/furniture/bdt/ee/rockingchair" },
        glue = {"ro.sapientia.furniture.bdt.ee.definition.rockingchair" },
        publish = false,
        dryRun = false)
public class RockingChairBDDEETest {
}
