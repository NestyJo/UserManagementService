package com.uhuru.userservice.controller;


import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.PermissionRequest;
import com.uhuru.userservice.service.PermissionInterface;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.1/permission")
@CrossOrigin
public class PermissionController {
    private final PermissionInterface permissionInterface;

    public PermissionController(PermissionInterface permissionInterface) {
        this.permissionInterface = permissionInterface;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllPermissions() {
        return permissionInterface.getAllPermissions();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addPermission(@Valid @RequestBody PermissionRequest permissionRequest) {
        return permissionInterface.addPermission(permissionRequest.getPermissionName(), permissionRequest.getDescription());
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<Object>> editPermission(@PathVariable Long permissionId, @Valid @RequestBody PermissionRequest permissionRequest) {
        return permissionInterface.editPermission(permissionId, permissionRequest.getPermissionName(), permissionRequest.getDescription());
    }
}
