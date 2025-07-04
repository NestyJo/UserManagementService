package com.uhuru.userservice.controller;

import com.uhuru.userservice.configuration.database.entities.UserDetails;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.UserDtoRequest;
import com.uhuru.userservice.service.RoleInterface;
import com.uhuru.userservice.service.UserInterface;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1.1/user")
@CrossOrigin
@Validated
public class UserController {
    private final UserInterface userInterface;
    private final RoleInterface roleInterface;


    public UserController(UserInterface userInterface, RoleInterface roleInterface) {
        this.userInterface = userInterface;
        this.roleInterface = roleInterface;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRole(@Valid @RequestBody UserDtoRequest request) {
        return userInterface.createUser(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDetails>>> getAllUsers() {
        return userInterface.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetails>> getUserById(@PathVariable Long userId) {
        return userInterface.getUserById(userId);
    }

    @GetMapping("/{userId}/permissions")
    public ResponseEntity<ApiResponse<Object>> getUserPermissionsById(@PathVariable Long userId) {
        return roleInterface.getUserPermissionByRole(userId.toString());
    }

    @PostMapping("enable/{userId}")
    public ResponseEntity<ApiResponse<Object>> enableUser(@PathVariable Long userId) {
        return userInterface.enableUser(userId);
    }

    @PostMapping("disable/{userId}")
    public ResponseEntity<ApiResponse<Object>> disable(@PathVariable Long userId) {
        return userInterface.disableUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDtoRequest request) {
        return userInterface.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Long userId) {
        return userInterface.deleteUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserDetails>>> searchUsers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String email) {
        return userInterface.searchUsers(firstName, lastName, email);
    }
}
