package com.uhuru.userservice.data.response;

public class PermissionResponse {
    private Long id;
    private String permissionName;
    private String description;

    public PermissionResponse(Long id, String permissionName, String description) {
        this.id = id;
        this.permissionName = permissionName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
