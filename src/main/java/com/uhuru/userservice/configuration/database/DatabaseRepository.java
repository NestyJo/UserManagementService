package com.uhuru.userservice.configuration.database;


import com.uhuru.userservice.configuration.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRepository {

    public final RoleRepository roleRepository;
    public final UserAccessRepository userAccessRepository;
    public final UserDetailsRepository userDetailsRepository;
    public final PermissionRepository permissionRepository;
    public final UserRoleRepository userRoleRepository;

    @Autowired
    public DatabaseRepository(RoleRepository roleRepository, UserAccessRepository userAccessRepository, UserDetailsRepository userDetailsRepository, PermissionRepository permissionRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userAccessRepository = userAccessRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.permissionRepository = permissionRepository;

        this.userRoleRepository = userRoleRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public UserAccessRepository getUserAccessRepository() {
        return userAccessRepository;
    }

    public UserDetailsRepository getUserDetailsRepository() {
        return userDetailsRepository;
    }

    public PermissionRepository getPermissionRepository() {
        return permissionRepository;
    }

    public UserRoleRepository getUserRoleRepository() {
        return userRoleRepository;
    }
}
