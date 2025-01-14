package ro.sapientia.furniture.bdt.component.definition;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ro.sapientia.furniture.model.Window;
import ro.sapientia.furniture.repository.WindowRepository;

@SpringBootTest
public class WindowStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WindowRepository windowRepository;

    @Given("the database contains the following windows:")
    public void theDatabaseContainsTheFollowingWindows(List<Map<String, String>> windows) {
        for (Map<String, String> windowData : windows) {
            Window window = new Window();
            window.setHeight(Integer.parseInt(windowData.get("height")));
            window.setWidth(Integer.parseInt(windowData.get("width")));
            window.setGlassType(windowData.get("glassType"));
            windowRepository.save(window);
        }
    }

    @When("I perform a GET request to {string}")
    public void iPerformAGetRequestTo(String endpoint) throws Exception {
        mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int status) throws Exception {
        MvcResult result = mockMvc.perform(get("/window/all")).andReturn();
        assertEquals(status, result.getResponse().getStatus());
    }

    @Then("the response should contain:")
    public void theResponseShouldContain(List<Map<String, String>> expectedWindows) throws Exception {
        MvcResult result = mockMvc.perform(get("/window/all").contentType(MediaType.APPLICATION_JSON)).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();

        for (Map<String, String> expectedWindow : expectedWindows) {
            assert jsonResponse.contains(expectedWindow.get("height"));
            assert jsonResponse.contains(expectedWindow.get("width"));
            assert jsonResponse.contains(expectedWindow.get("glassType"));
        }
    }
}
