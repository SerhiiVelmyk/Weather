package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import utils.DriverManager;

import static org.slf4j.LoggerFactory.getLogger;

abstract class AbstractPage {
    private WebDriver driver;
    private static final int DELAY = 30;
    private static final Logger LOGGER = getLogger(AbstractPage.class);

    AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    WebDriver getDriver() {
        return driver;
    }

    String setText(WebElement element, String value) {
        element.click();
        element.clear();
        element.clear();
        element.sendKeys(value);
        return value;
    }

    public boolean waitForElement(final WebElement element) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(DriverManager.getDriver(), DELAY);
            webDriverWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean waitElementToBeClickable(final WebElement element) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(DriverManager.getDriver(), DELAY);
            webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
        return true;
    }
}
