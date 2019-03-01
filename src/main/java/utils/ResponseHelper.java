package utils;

import com.jayway.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseHelper {
    private Response response;

    public ResponseHelper() {
    }
}
