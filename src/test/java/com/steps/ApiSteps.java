package com.steps;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import utils.HttpResponseHolder;
import utils.HttpSetupHelper;
import utils.RequestHelper;
import utils.ResponseHelper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.jayway.restassured.RestAssured.given;
import static consts.ApiConst.WEATHER_END_POINT_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.slf4j.LoggerFactory.getLogger;
import static consts.ApiConst.SEARCH_END_POINT_URL;

public class ApiSteps {
    private static HttpResponseHolder response;
    private static String url;
    private static final Logger LOGGER = getLogger(ApiSteps.class);
    private RequestHelper requestHelper = new RequestHelper();
    private ResponseHelper responseHelper = new ResponseHelper();

    @Given("set up GET data for search in circle end point")
    public void getDataFromEndPoint(DataTable dataTable) throws URISyntaxException {
        List<String> params = new ArrayList<>(dataTable.asList(String.class));
        LOGGER.info("Concatenate parameters for end point " + params);
        url = new URIBuilder(SEARCH_END_POINT_URL)
                .addParameter("lon", params.get(0))
                .addParameter("lat", params.get(1))
                .addParameter("cnt", params.get(2))
                .build()
                .toString();
    }

    @When("execute get request")
    public void executeGetRequest() {
        LOGGER.info("Call end point with url " + url);
        response = HttpSetupHelper.executeGetRequest(url);
    }

    @Given("parameter name (.*) and parameter value (.*) for end point")
    public void specifyParameter(String parameter, String value) {
        LOGGER.info("Concatenate parameters " + parameter + " and values " + value);

        String[] parameters = parameter.split(", ");
        String[] values = value.split(", ");
        RequestSpecification requestSpecification = given();

        for (int i = 0; i < parameters.length; i++) {
            requestSpecification.param(parameters[i], values[i]);
        }
        requestHelper.setRequest(requestSpecification);
    }

    @When("call (.*) end point")
    public void callEndPoint(String endPoint) {
        LOGGER.info("Call end point " + endPoint);
        switch (endPoint) {
            case ("Weather"):
                responseHelper.setResponse(requestHelper.getRequest().when().get(WEATHER_END_POINT_URL));
                break;
            case (""):
//                TODO
            default:
                throw new AssertionError(endPoint + " such endpoint does not exist");
        }
    }

    @Then("assert that body parameter (.*) equal to value (.*)")
    public void verifyValueInParameter(String parameter, String value) {
        responseHelper.getResponse().then()
                .assertThat().contentType(ContentType.JSON)
                .assertThat().statusCode(200)
                .assertThat().body(parameter, equalTo(value));
    }

    @Then("assert error message and status code")
    public void verifyInvalidData() {
        responseHelper.getResponse().then()
                .assertThat().statusCode(404)
                .and().body("message", equalTo("city not found"));
    }

    @Then("response status code is (.*)")
    public void verifyStatusCode(int value) {
        assertEquals(value, response.getStatusCode(), "Wrong HTTP status code");
    }

//    TODO other checks
}
