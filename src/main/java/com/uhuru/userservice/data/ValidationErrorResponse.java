package com.uhuru.userservice.data;



public class ValidationErrorResponse {
    private String field;
    private String message;


    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
