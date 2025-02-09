package com.uhuru.userservice.service;

import com.uhuru.userservice.configuration.database.entities.Role;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.RoleDto;
import com.uhuru.userservice.data.response.RoleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleInterface {

    ResponseEntity<ApiResponse<Object>> createRole(RoleDto role);

    ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles();

    ResponseEntity<ApiResponse<Object>> getRoleById(Long id);

    ResponseEntity<ApiResponse<Object>> updateRole(Long id, RoleDto roleDetails);

    ResponseEntity<ApiResponse<Object>> deleteRole(Long id);

    ResponseEntity<ApiResponse<Object>> getPermissionsForRole(String roleName);
}
