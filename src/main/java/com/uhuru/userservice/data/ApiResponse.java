package com.uhuru.userservice.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;


    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {}
}
