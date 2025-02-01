package com.uhuru.userservice.controller;

import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.RoleDto;
import com.uhuru.userservice.data.response.RoleResponse;
import com.uhuru.userservice.service.RoleInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.1/role")
@CrossOrigin
public class RoleController {

    private final RoleInterface roleInterface;

    public RoleController(RoleInterface roleInterface) {
        this.roleInterface = roleInterface;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createRole(@RequestBody RoleDto role) {
        return roleInterface.createRole(role);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        return roleInterface.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getRoleById(@PathVariable Long id) {
        return roleInterface.getRoleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDetails) {
        return roleInterface.updateRole(id, roleDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRole(@PathVariable Long id) {
        return roleInterface.deleteRole(id);
    }
}
