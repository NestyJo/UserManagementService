package com.uhuru.userservice.controller;

import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.UserDtoRequest;
import com.uhuru.userservice.service.UserInterface;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1.1/user")
@CrossOrigin
public class UserController {
    private final UserInterface userInterface;

    public UserController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRole(@Valid @RequestBody UserDtoRequest request) {
        return userInterface.createUser(request);
    }
}
