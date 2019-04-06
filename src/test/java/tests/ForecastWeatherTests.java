package tests;

import org.domain.Coordinates;
import org.domain.ForecastData;
import org.domain.WeatherData;
import org.enums.Lang;
import org.enums.Type;
import org.enums.Units;
import org.exeption.InvalidResponseException;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsNull;
import org.repo.ForecastRepo;
import org.repo.ForecastRepoImpl;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.utils.QueryMapper;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ForecastWeatherTests {
    private ForecastRepo forecastDataRepository;

    @BeforeSuite
    public void setUp() {
        forecastDataRepository = new ForecastRepoImpl();
    }

    @Test
    public void verifyForecastWeatherByZipCodeAndCountryCode() throws InvalidResponseException {
        String zipCode = "60563";
        String countryCode = "US";
        QueryMapper queryMapper = new QueryMapper();
        queryMapper.setCityZipCode(zipCode + "," + countryCode);

        ForecastData forecastData = forecastDataRepository.getForecastData(queryMapper);
        assertThat(forecastData.getCod(), equalTo(200));
        assertThat(forecastData.getCity().getName(), equalTo("Naperville"));
        assertThat(forecastData.getCity().getCountry(), equalTo("US"));
        for (WeatherData data : forecastData.getList()) {
            assertThat(data.getWeather(), not(IsEmptyCollection.empty()));
            assertThat(data.getMain(), is(IsNull.notNullValue()));
        }
        forecastDataRepository.getResponse().then().body(matchesJsonSchemaInClasspath("forecastJsonSchema.json"));
    }

    @Test
    public void verifyForecastWeatherByCityName() throws InvalidResponseException {
        String cityName = "Berlin";
        String countryCode = "DE";
        QueryMapper queryMapper = new QueryMapper();
        queryMapper.setCityName(cityName + "," + countryCode)
                .setLang(Lang.CHINESE_SIMPLIFIED)
                .setUnits(Units.FAHRENHEIT)
                .setAccuracy(Type.LIKE);
        ForecastData forecastData = forecastDataRepository.getForecastData(queryMapper);

        assertThat(forecastData.getCod(), equalTo(200));
        assertThat(forecastData.getCity().getName(), equalTo(cityName));
        assertThat(forecastData.getCity().getCountry(), equalTo("DE"));
        for (WeatherData data : forecastData.getList()) {
            assertThat(data.getWeather(), not(IsEmptyCollection.empty()));
            assertThat(data.getMain(), is(IsNull.notNullValue()));
        }
        forecastDataRepository.getResponse().then().body(matchesJsonSchemaInClasspath("forecastJsonSchema.json"));
    }

    @Test
    public void verifyForecastWeatherByCoordinates() throws InvalidResponseException {
        Coordinates coordinates = Coordinates.builder().lat(35.02).lon(139.01).build();
        QueryMapper queryMapper = new QueryMapper();
        queryMapper.setCoordinates(coordinates);
        ForecastData forecastData = forecastDataRepository.getForecastData(queryMapper);

        assertThat(forecastData.getCod(), equalTo(200));
        for (WeatherData data : forecastData.getList()) {
            assertThat(data.getWeather(), not(IsEmptyCollection.empty()));
            assertThat(data.getMain(), is(IsNull.notNullValue()));
        }
        forecastDataRepository.getResponse().then().body(matchesJsonSchemaInClasspath("forecastJsonSchema.json"));
    }

    @Test(expectedExceptions = InvalidResponseException.class,
            expectedExceptionsMessageRegExp = "404 : city not found")
    public void verifyErrorForNonExistingCity() throws InvalidResponseException {
        String cityName = "test";
        QueryMapper queryMapper = new QueryMapper();
        queryMapper.setCityName(cityName);

        forecastDataRepository.getForecastData(queryMapper);
    }

    @Test(expectedExceptions = InvalidResponseException.class,
            expectedExceptionsMessageRegExp = "400 : 100.0 is not a float")
    public void verifyErrorForNonExistingCoordinates() throws InvalidResponseException {
        Coordinates coordinates = Coordinates.builder().lat(100).lon(100).build();
        QueryMapper queryMapper = new QueryMapper();
        queryMapper.setCoordinates(coordinates);

        forecastDataRepository.getForecastData(queryMapper);
    }
}
