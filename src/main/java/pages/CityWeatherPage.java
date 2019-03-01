package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CityWeatherPage extends AbstractPage {

    @FindBy(xpath = "//div[@class='today_nowcard-temp']/span")
    private WebElement temperature;

    @FindBy(xpath = "(//span[contains(@classname, 'locationName')])[last()]")
    private WebElement location;

    @FindBy(xpath = "//th[text()='Humidity']/following-sibling::td/span/span")
    private WebElement humidity;

    @FindBy(xpath = "//div[@class='today_nowcard-phrase']")
    private WebElement weatherDescription;

    public CityWeatherPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getCityElement() {
        return location;
    }

    public WebElement getTemperatureElement() {
        return temperature;
    }

    public WebElement getHumidity() {
        return humidity;
    }

    public WebElement getWeatherDescription() {
        return weatherDescription;
    }
}
