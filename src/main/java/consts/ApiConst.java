package consts;

public class ApiConst {
    private static final String AMPERSAND = "&";
    private static final String BASE_API_URL = "http://api.openweathermap.org/data/2.5";
    public static final Integer DEFAULT_PORT = 80;
    private static final String API_KEY = "e09c621172e6157f25676a10b4cfedbb";
    public static final String CITY_END_POINT_URL = BASE_API_URL + "/box/city?appid=" + API_KEY + AMPERSAND;
    public static final String SEARCH_END_POINT_URL = BASE_API_URL + "/find?appid=" + API_KEY + AMPERSAND;
    public static final String GROUP_END_POINT_URL = BASE_API_URL + "/group?appid=" + API_KEY + AMPERSAND;
    public static final String WEATHER_END_POINT_URL = BASE_API_URL + "/weather?appid=" + API_KEY + AMPERSAND;
    public static final String FORECAST_END_POINT_URL = BASE_API_URL + "/forecast?appid=" + API_KEY + AMPERSAND;
}
