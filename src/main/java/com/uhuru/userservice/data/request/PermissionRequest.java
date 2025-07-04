package com.uhuru.userservice.data.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PermissionRequest {
    @NotNull(message = "Permission name cannot be null")
    @Size(min = 3, max = 50, message = "Permission name must be between 3 and 50 characters")
    private String permissionName;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    public @NotNull(message = "Permission name cannot be null") @Size(min = 3, max = 50, message = "Permission name must be between 3 and 50 characters") String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(@NotNull(message = "Permission name cannot be null") @Size(min = 3, max = 50, message = "Permission name must be between 3 and 50 characters") String permissionName) {
        this.permissionName = permissionName;
    }

    public @Size(max = 255, message = "Description cannot exceed 255 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 255, message = "Description cannot exceed 255 characters") String description) {
        this.description = description;
    }
}
