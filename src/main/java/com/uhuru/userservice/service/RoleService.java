package com.uhuru.userservice.service;


import com.uhuru.userservice.configuration.database.DatabaseRepository;
import com.uhuru.userservice.configuration.database.entities.Permission;
import com.uhuru.userservice.configuration.database.entities.Role;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.RoleDto;
import com.uhuru.userservice.data.response.RoleResponse;
import com.uhuru.userservice.utility.LoggerService;
import com.uhuru.userservice.utility.ResponseUtil;
import com.uhuru.userservice.utility.UtilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService implements RoleInterface {

    private final DatabaseRepository databaseRepository;
    private final UtilityService utilityService;
    private final LoggerService loggerService;

    public RoleService(DatabaseRepository databaseRepository, UtilityService utilityService, LoggerService loggerService) {
        this.databaseRepository = databaseRepository;
        this.utilityService = utilityService;
        this.loggerService = loggerService;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> createRole(RoleDto role) {
        try {
            Optional<Role> existingRole = Optional.ofNullable(databaseRepository.getRoleRepository().findByName(role.getRoleName()));

            if (existingRole.isPresent()) {
                return ResponseUtil.error("Role with the same name already exists", HttpStatus.CONFLICT);
            }

            Role newRole = new Role();
            newRole.setName(role.getRoleName());
            Role createdRole = databaseRepository.getRoleRepository().save(newRole);

            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRoleId(createdRole.getId());
            roleResponse.setRoleName(createdRole.getName());
            roleResponse.setDescription(createdRole.getDescription());

            return ResponseUtil.success(true, "Role created successfully", roleResponse);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to create role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        try {
            List<Role> roles = databaseRepository.getRoleRepository().findAll();
            loggerService.log("Retrieved roles: {}", String.valueOf(roles.size()));

            List<RoleResponse> roleResponses = new ArrayList<>();
            for (Role role : roles) {
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setRoleId(role.getId());
                roleResponse.setRoleName(role.getName());
                roleResponse.setDescription(role.getDescription());
                roleResponses.add(roleResponse);
            }

            return ResponseUtil.success(true, "Roles retrieved successfully", roleResponses);
        } catch (Exception e) {
            loggerService.log("Failed to retrieve roles: {}", e.getMessage());
            return ResponseUtil.error("Failed to retrieve roles: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getRoleById(Long id) {
        try {
            Role role = databaseRepository.getRoleRepository().findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
            return ResponseUtil.success(true, "Role retrieved successfully", role);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to retrieve role: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> updateRole(Long id, RoleDto roleDetails) {
        try {
            Role role = databaseRepository.getRoleRepository().findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
            role.setName(roleDetails.getRoleName());
            Role updatedRole = databaseRepository.getRoleRepository().save(role);
            return ResponseUtil.success(true, "Role updated successfully", updatedRole);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to update role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> deleteRole(Long id) {
        try {
            Role role = databaseRepository.getRoleRepository().findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
            databaseRepository.getRoleRepository().delete(role);
            return ResponseUtil.success(true, "Role deleted successfully", null);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to delete role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getPermissionsForRole(String roleName) {
        try {
            Optional<Role> roleOptional = Optional.ofNullable(databaseRepository.getRoleRepository().findByName(roleName));

            if (roleOptional.isEmpty()) {
                return ResponseUtil.error("Role with the name '" + roleName + "' does not exist", HttpStatus.NOT_FOUND);
            }

            Role role = roleOptional.get();

            Set<String> permissions = role.getPermissions().stream().map(Permission::getPermissionName).collect(Collectors.toSet());

            return ResponseUtil.success(true, "Permissions retrieved successfully", permissions);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while retrieving permissions: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<ApiResponse<Object>> addPermissionsToRole(String roleName, List<String> permissionNames) {
        return modifyRolePermissions(roleName, permissionNames, true);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> removePermissionsFromRole(String roleName, List<String> permissionNames) {
        return modifyRolePermissions(roleName, permissionNames, false);
    }

    private ResponseEntity<ApiResponse<Object>> modifyRolePermissions(String roleName, List<String> permissionNames, boolean isAddOperation) {
        try {
            Optional<Role> roleOptional = Optional.ofNullable(databaseRepository.getRoleRepository().findByName(roleName));

            if (roleOptional.isEmpty()) {
                return ResponseUtil.error("Role with the name '" + roleName + "' does not exist", HttpStatus.NOT_FOUND);
            }

            Role role = roleOptional.get();
            Set<Permission> permissionsToModify = new HashSet<>();

            for (String permissionName : permissionNames) {
                Permission permission = databaseRepository.getPermissionRepository().findByPermissionName(permissionName);
                if (permission != null) {
                    permissionsToModify.add(permission);
                } else {
                    return ResponseUtil.error("Permission with the name '" + permissionName + "' does not exist", HttpStatus.NOT_FOUND);
                }
            }

            if (isAddOperation) {
                role.getPermissions().addAll(permissionsToModify);
            } else {
                role.getPermissions().removeAll(permissionsToModify);
            }

            databaseRepository.getRoleRepository().save(role);

            String operation = isAddOperation ? "added to" : "removed from";
            return ResponseUtil.success(true, "Permissions " + operation + " role successfully", null);

        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while modifying permissions for role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
