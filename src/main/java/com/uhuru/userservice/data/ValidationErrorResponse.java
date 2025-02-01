package com.uhuru.userservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationErrorResponse {
    private final String field;
    private final String message;

    public ValidationErrorResponse(@JsonProperty("field") String field, @JsonProperty("message") String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
