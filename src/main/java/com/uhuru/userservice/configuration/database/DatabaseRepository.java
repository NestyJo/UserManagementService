package com.uhuru.userservice.configuration.database;


import com.uhuru.userservice.configuration.database.repository.RoleRepository;
import com.uhuru.userservice.configuration.database.repository.UserAccessRepository;
import com.uhuru.userservice.configuration.database.repository.UserDetailsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRepository {

    public final RoleRepository roleRepository;
    public final UserAccessRepository userAccessRepository;
    public final UserDetailsRepository userDetailsRepository;

    @Autowired
    public DatabaseRepository(RoleRepository roleRepository, UserAccessRepository userAccessRepository, UserDetailsRepository userDetailsRepository) {
        this.roleRepository = roleRepository;
        this.userAccessRepository = userAccessRepository;
        this.userDetailsRepository = userDetailsRepository;
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
}
