package com.uhuru.userservice.service;

import com.uhuru.userservice.configuration.database.entities.UserDetails;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.UserDtoRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInterface {
    ResponseEntity<ApiResponse<Object>> createUser(UserDtoRequest user);
    ResponseEntity<ApiResponse<UserDetails>> getUserById(Long userId);

    ResponseEntity<ApiResponse<List<UserDetails>>> getAllUsers();

    ResponseEntity<ApiResponse<Object>> updateUser(Long userId, UserDtoRequest request);

    ResponseEntity<ApiResponse<Object>> deleteUser(Long userId);

    ResponseEntity<ApiResponse<List<UserDetails>>> searchUsers(String firstName, String lastName, String email);

    ResponseEntity<ApiResponse<Object>> enableUser(Long userId);

    ResponseEntity<ApiResponse<Object>> disableUser(Long userId);


}
