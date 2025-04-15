package ro.sapientia.furniture.bdt.chair;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import ro.sapientia.chair.FurnitureApplication;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/eetest/resources/ro/sapientia/furniture/bdt/ee_chair" }, glue = {
		"ro.sapientia.furniture.bdt.chair.ee.test" }, publish = false, dryRun = false)
public class FurnitureBDDEEChairTest {

}
