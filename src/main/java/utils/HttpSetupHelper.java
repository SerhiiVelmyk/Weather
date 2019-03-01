package utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;

public class HttpSetupHelper {
    private static CloseableHttpClient httpClient = WinHttpClients.createSystem();
    private static HttpCoreContext httpContext = HttpCoreContext.create();
    private static final RequestConfig REQUEST_CONFIG
            = RequestConfig.custom().setCircularRedirectsAllowed(true).build();

    public static HttpResponseHolder executeGetRequest(String endpoint) {
        HttpGet httpGet = new HttpGet(endpoint);
        httpGet.setConfig(REQUEST_CONFIG);
        HttpResponseHolder responseHolder;
        try (CloseableHttpResponse response = httpClient.execute(httpGet, httpContext)) {
            Header[] allHeaders = response.getAllHeaders();
            LinkedHashMap<String, String> headersMap = new LinkedHashMap<>();
            Arrays.stream(allHeaders)
                    .forEach(header -> headersMap.put(header.getName(), header.getValue()));
            responseHolder = new HttpResponseHolder(response.getStatusLine().getStatusCode(),
                    headersMap,
                    getResponseBodyAsString(response));
            return responseHolder;
        } catch (IOException e) {
            return null;
        }
    }

    private static String getResponseBodyAsString(HttpResponse response) {
        if (Objects.nonNull(response)) {
            try {
                return EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
}
