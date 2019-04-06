package org.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinates {
    @JsonAlias({"lat", "Lat"})
    private double lat;

    @JsonAlias({"lon", "Lon"})
    private double lon;

    public Coordinates() {
    }
}