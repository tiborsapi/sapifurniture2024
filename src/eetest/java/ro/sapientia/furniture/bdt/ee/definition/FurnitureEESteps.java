package ro.sapientia.furniture.bdt.ee.definition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FurnitureEESteps {

    private Response lastResponse;

    static {
        String host = System.getProperty("HOST", System.getenv().getOrDefault("HOST", "localhost"));
        String port = System.getProperty("PORT", System.getenv().getOrDefault("PORT", "4080"));
        RestAssured.baseURI = "http://" + host;
        RestAssured.port = Integer.parseInt(port);
    }

    @Given("that we have the following furniture bodies:")
    public void that_we_have_the_following_furniture_bodies(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String json = String.format("{\"width\": %s, \"heigth\": %s, \"depth\": %s}",
                    row.get("width"), row.get("heigth"), row.get("depth"));
            given()
                .contentType("application/json")
                .body(json)
            .when()
                .post("/api/furniture")
            .then()
                .statusCode(201);
        }
    }

    @When("I invoke the furniture all endpoint")
    public void i_invoke_the_furniture_all_endpoint() {
        lastResponse = given()
            .accept("application/json")
        .when()
            .get("/api/furniture/all")
        .then()
            .statusCode(200)
            .extract()
            .response();
    }

    @Then("I should get the heigth {string} for the position {string}")
    public void i_should_get_the_heigth_for_the_position(String expectedHeigth, String position) {
        int pos = Integer.parseInt(position);
        List<Object> heigths = lastResponse.jsonPath().getList("heigth");
        Assert.assertTrue("Response does not contain enough elements", heigths.size() > pos);
        String actual = String.valueOf(heigths.get(pos));
        Assert.assertEquals(expectedHeigth, actual);
    }
}