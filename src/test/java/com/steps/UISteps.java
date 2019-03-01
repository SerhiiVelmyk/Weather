package com.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import pages.CityWeatherPage;
import pages.WeatherMainPage;
import utils.DriverManager;

import static com.jayway.restassured.RestAssured.given;
import static consts.ApiConst.WEATHER_END_POINT_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static utils.DriverManager.getDriver;

public class UISteps {
    private String searchValue;
    private WeatherMainPage weatherMainPage = PageFactory.initElements(getDriver(), WeatherMainPage.class);
    private CityWeatherPage cityWeatherPage = PageFactory.initElements(getDriver(), CityWeatherPage.class);
    private static final Logger LOGGER = getLogger(UISteps.class);

    @Given("I open main page")
    public void openMainPage() {
        DriverManager.getDriver();
        weatherMainPage.openMainPage();
        assertTrue(weatherMainPage.waitElementToBeClickable(weatherMainPage.getSearchInput()), "Page is not loaded");
    }

    @And("I set (.*) to the search")
    public void setValueToSearch(String value) {
        LOGGER.info("Set value " + value + " to the search input");
        searchValue = weatherMainPage.setSearchValue(value);
        assertTrue(weatherMainPage.waitElementToBeClickable(weatherMainPage.foundResult(searchValue)),
                "Search results are empty");
    }

    @When("I click to the first searched result")
    public void clickButton() {
        LOGGER.info("Click to first found city");
        weatherMainPage.getFirstResult().click();
        assertTrue(weatherMainPage.waitForElement(cityWeatherPage.getCityElement()),
                "Data on searched page was not loaded");
        assertTrue(weatherMainPage.waitForElement(cityWeatherPage.getTemperatureElement()),
                "Data on searched page was not loaded");
    }

    @Then("Searched data by (.*) is correct comparing with UI")
    public void compareAPIDataWithUI(String value) {
        LOGGER.info("Compare API data with UI");
        String paramName = "zip".equals(value) ? "zip" : "q";
        given()
                .param(paramName, searchValue)
                .when()
                .get(WEATHER_END_POINT_URL)
                .then()
                .assertThat().body("main.temp", equalTo(cityWeatherPage.getTemperatureElement()
                .getText().split("°")[0]))
                .and().body("main.humidity", equalTo(cityWeatherPage.getHumidity().getText()))
                .and().body("name", equalTo(cityWeatherPage.getCityElement().getText().split(",")[0]))
                .and().body("weather.description", equalTo(cityWeatherPage.getWeatherDescription().getText()));
    }
}
