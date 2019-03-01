package com;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;

import java.util.Collection;

import static org.openqa.selenium.OutputType.BYTES;
import static org.slf4j.LoggerFactory.getLogger;
import static utils.DriverManager.getDriver;

public class GlobalHook {
    private static final Logger LOGGER = getLogger(GlobalHook.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("Test started " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        Collection<String> sourceTagNames = scenario.getSourceTagNames();
        if (scenario.isFailed()) {
            if (sourceTagNames.contains("@ui")) {
                scenario.embed(((TakesScreenshot) getDriver()).getScreenshotAs(BYTES), "image/png");
//                File screenshot = doScreenshot(getDriver());
//                saveScreenshot(screenshot, scenario);
            }
            LOGGER.info("Test is failed " + scenario.getName());
        } else {
            LOGGER.info("Test is passed " + scenario.getName());
        }
    }
}
