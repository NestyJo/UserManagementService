package com.uhuru.userservice.controller;


import com.uhuru.userservice.configuration.database.entities.Role;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.RoleDto;
import com.uhuru.userservice.service.RoleInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.1/role")
@CrossOrigin
public class RoleController {

    private  final RoleInterface roleInterface;

    public RoleController(RoleInterface roleInterface) {
        this.roleInterface = roleInterface;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRole(@RequestBody RoleDto role) {
        return roleInterface.createRole(role);
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        return roleInterface.getAllRoles();
    }

    // Get a role by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getRoleById(@PathVariable Long id) {
        return roleInterface.getRoleById(id);
    }

    // Update a role
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDetails) {
        return roleInterface.updateRole(id, roleDetails);
    }

    // Delete a role
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRole(@PathVariable Long id) {
        return roleInterface.deleteRole(id);
    }
}
