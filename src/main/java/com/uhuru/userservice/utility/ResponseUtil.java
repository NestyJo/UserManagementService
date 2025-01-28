package com.uhuru.userservice.utility;

import com.uhuru.userservice.data.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success( boolean success, String message,T data) {
        ApiResponse<T> response = new ApiResponse<>(success, message, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T>  ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status){
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        return new ResponseEntity<>(response, status);
    }
}
