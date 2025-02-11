package com.uhuru.userservice.service;


import com.uhuru.userservice.configuration.database.DatabaseRepository;
import com.uhuru.userservice.configuration.database.entities.Permission;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.utility.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService implements PermissionInterface {
    private final DatabaseRepository databaseRepository;

    public PermissionService(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> addPermission(String permissionName, String description) {
        try {
            if (databaseRepository.getPermissionRepository().findByPermissionName(permissionName) != null) {
                return ResponseUtil.error("Permission with name '" + permissionName + "' already exists", HttpStatus.CONFLICT);
            }

            Permission newPermission = new Permission();
            newPermission.setPermissionName(permissionName);
            newPermission.setDescription(description);

            databaseRepository.getPermissionRepository().save(newPermission);

            return ResponseUtil.success(true, "Permission added successfully", null);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while adding permission: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getAllPermissions() {
        try {
            List<String> permissions = databaseRepository.getPermissionRepository().findAll()
                    .stream()
                    .map(Permission::getPermissionName)
                    .collect(Collectors.toList());

            return ResponseUtil.success(true, "Permissions retrieved successfully", permissions);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while retrieving permissions: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> editPermission(Long permissionId, String permissionName, String description) {
        try {
            Optional<Permission> permissionOptional = databaseRepository.getPermissionRepository().findById(permissionId);

            if (permissionOptional.isEmpty()) {
                return ResponseUtil.error("Permission with ID '" + permissionId + "' does not exist", HttpStatus.NOT_FOUND);
            }

            Permission permission = permissionOptional.get();
            permission.setPermissionName(permissionName);
            permission.setDescription(description);

            databaseRepository.getPermissionRepository().save(permission);

            return ResponseUtil.success(true, "Permission updated successfully", null);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while updating permission: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
