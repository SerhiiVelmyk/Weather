package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class WeatherMainPage extends AbstractPage {

    private static final String URL = "http://weather.com/";
    private static final Logger LOGGER = getLogger(WeatherMainPage.class);

    @FindBy(xpath = "//input[contains(@class, 'theme')]")
    private WebElement searchInput;

    @FindBy(xpath = "//div[text()='Search results']/following-sibling::ul//a")
    private List<WebElement> searchResults;

    public WeatherMainPage(WebDriver driver) {
        super(driver);
    }

    public void openMainPage() {
        LOGGER.info("Open " + URL);
        getDriver().get(URL);
    }

    public String setSearchValue(String value) {
        return setText(searchInput, value);
    }

    public WebElement getSearchInput() {
        return searchInput;
    }

    private List<WebElement> getSearchResults() {
        return searchResults;
    }

    public WebElement getFirstResult() {
        return getSearchResults().get(0);
    }

    public WebElement foundResult(String value) {
        return getDriver().findElement(
                By.xpath("//div[text()='Search results']/following-sibling::ul//a[contains(text(),'" + value + "')]"));
    }
}
