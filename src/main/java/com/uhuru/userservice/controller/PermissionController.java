package com.uhuru.userservice.controller;


import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.service.PermissionInterface;
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
    public ResponseEntity<ApiResponse<Object>> addPermission(@RequestParam String permissionName, @RequestParam String description) {
        return permissionInterface.addPermission(permissionName, description);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<Object>> editPermission(@PathVariable Long permissionId, @RequestParam String newPermissionName, @RequestParam String newDescription) {
        return permissionInterface.editPermission(permissionId, newPermissionName, newDescription);
    }
}
