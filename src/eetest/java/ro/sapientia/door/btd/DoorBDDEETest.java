package ro.sapientia.door.btd;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

	
@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/eetest/resources/ro/sapientia/door/ee"},
	    glue = {"ro.sapientia.door.bdt.ee.definition" },
	    publish = false,
	    dryRun = true)
public class DoorBDDEETest {
}
