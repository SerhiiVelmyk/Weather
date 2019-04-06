package org.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class Weather {
    private long id;
    private String description;
    private String main;
    private String icon;

    public Weather() {
    }
}
