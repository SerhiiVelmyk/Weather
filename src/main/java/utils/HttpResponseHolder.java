package utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
public class HttpResponseHolder {
    private int statusCode;

    private LinkedHashMap<String, String> headers;

    private String responseBody;
}
