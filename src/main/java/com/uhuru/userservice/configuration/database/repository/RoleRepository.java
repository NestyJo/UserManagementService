package com.uhuru.userservice.configuration.database.repository;

import com.uhuru.userservice.configuration.database.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
