package com;

import com.runners.AfterSuite;
import com.runners.BeforeSuite;
import com.runners.ExtendedRunner;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import utils.DriverManager;
import utils.Reporter;

import static org.slf4j.LoggerFactory.getLogger;

@CucumberOptions(features = {"classpath:tests"},
        plugin = {"pretty", "json:./target/report/results.json"},
        monochrome = true)
@RunWith(ExtendedRunner.class)
public class RegressionTestsRunner {
    private static final Logger LOGGER = getLogger(RegressionTestsRunner.class);

    @BeforeSuite
    public static void setUp() {
        LOGGER.info("Regression suite is started");
    }

    @AfterSuite
    public static void tearDown() {
        DriverManager.closeDriver();
        LOGGER.info("Regression suite is finished");
        Reporter.generateReport();
    }
}
