package com.uhuru.userservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ValidationErrorResponse {
    private final String field;
    private final String message;

    public ValidationErrorResponse(@JsonProperty("field") String field, @JsonProperty("message") String message) {
        this.field = field;
        this.message = message;
    }

}
