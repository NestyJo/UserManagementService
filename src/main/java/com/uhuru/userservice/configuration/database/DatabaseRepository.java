package com.uhuru.userservice.configuration.database;


import com.uhuru.userservice.configuration.database.repository.PermissionRepository;
import com.uhuru.userservice.configuration.database.repository.RoleRepository;
import com.uhuru.userservice.configuration.database.repository.UserAccessRepository;
import com.uhuru.userservice.configuration.database.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRepository {

    public final RoleRepository roleRepository;
    public final UserAccessRepository userAccessRepository;
    public final UserDetailsRepository userDetailsRepository;
    public final PermissionRepository permissionRepository;

    @Autowired
    public DatabaseRepository(RoleRepository roleRepository, UserAccessRepository userAccessRepository, UserDetailsRepository userDetailsRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.userAccessRepository = userAccessRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.permissionRepository = permissionRepository;
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
}
