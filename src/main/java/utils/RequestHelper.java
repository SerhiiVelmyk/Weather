package utils;

import com.jayway.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestHelper {
    private RequestSpecification request;

    public RequestHelper() {
    }
}
