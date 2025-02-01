package com.uhuru.userservice.service;

import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.RoleDto;
import com.uhuru.userservice.data.request.UserDtoRequest;
import org.springframework.http.ResponseEntity;

public interface UserInterface {
    ResponseEntity<ApiResponse<Object>> createUser(UserDtoRequest user);
}
