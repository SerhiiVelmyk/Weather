package org.repo;

import com.jayway.restassured.response.Response;
import org.domain.ForecastData;
import org.exeption.InvalidResponseException;
import org.utils.QueryMapper;

public interface ForecastRepo {
    ForecastData getForecastData(QueryMapper queryParameters) throws InvalidResponseException;
    Response getResponse();
}
