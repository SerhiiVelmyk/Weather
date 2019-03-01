package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static consts.PropertiesNames.PATH_TO_CHROME_BINARY;
import static org.slf4j.LoggerFactory.getLogger;

public class DriverManager {
    private static final Logger LOGGER = getLogger(DriverManager.class);
    private static final int DELAY = 120;
    private static volatile WebDriver driver = null;
    private static String sep = File.separator;
    private static final String PATH_TO_RESOURCES = System.getProperty("user.dir") + sep + "src" + sep + "main" + sep
            + "resources" + sep;

    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            switch (PropertyReader.getBrowserName()) {
                case ("FireFox"):
                    driver = getFireFoxDriver();
                    break;
                case ("Chrome"):
                    driver = getChromeDriver();
                    break;
                default:
                    throw new AssertionError("Browser is not recognized");
            }
        }
        return driver;
    }

    private static WebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_RESOURCES + "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        String path = PropertyReader.getGlobalProperty(PATH_TO_CHROME_BINARY);
        options.setBinary(path);
        options.addArguments("disable-inforbars");
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(DELAY, TimeUnit.SECONDS);
        return driver;
    }

    private static WebDriver getFireFoxDriver() {
        System.setProperty("webdriver.gecko.driver", PATH_TO_RESOURCES + "geckodriver.exe");
        driver = new FirefoxDriver();
        LOGGER.info("Open FireFox browser");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(DELAY, TimeUnit.SECONDS);
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            getDriver().quit();
            driver = null;
            LOGGER.info("Close browser");
        }
    }
}
