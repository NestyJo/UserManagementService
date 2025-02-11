package com.uhuru.userservice.service;


import com.uhuru.userservice.data.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface PermissionInterface {

    ResponseEntity<ApiResponse<Object>> addPermission(String permissionName, String description);

    ResponseEntity<ApiResponse<Object>> getAllPermissions();

    ResponseEntity<ApiResponse<Object>> editPermission(Long permissionId, String permissionName, String description);
}
