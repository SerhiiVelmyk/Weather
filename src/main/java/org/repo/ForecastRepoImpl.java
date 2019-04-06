package org.repo;

import com.jayway.restassured.response.Response;
import org.api.ApiHelper;
import org.domain.ForecastData;
import org.exeption.InvalidResponseException;
import org.utils.QueryMapper;

public class ForecastRepoImpl implements ForecastRepo {
    private static final String FORECAST_WEATHER_PATH = "/forecast";
    private ApiHelper apiHelper;
    private Response response;

    public ForecastRepoImpl() {
        this.apiHelper = new ApiHelper();
    }

    @Override
    public ForecastData getForecastData(QueryMapper queryParameters) throws InvalidResponseException {
        ForecastData forecastWeatherData;
        response = apiHelper.getWithParameters(FORECAST_WEATHER_PATH, queryParameters.build());
        try {
            forecastWeatherData = response.as(ForecastData.class);
        } catch (Exception e) {
            throw new InvalidResponseException(response.path("cod"), response.path("message"));
        }

        System.out.println("Response body: " + response.getBody().asString());
        return forecastWeatherData;
    }

    @Override
    public Response getResponse() {
        return response;
    }
}
