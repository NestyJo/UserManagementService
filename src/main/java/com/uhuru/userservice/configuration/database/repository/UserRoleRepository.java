package com.uhuru.userservice.configuration.database.repository;

import com.uhuru.userservice.configuration.database.entities.UserRole;
import com.uhuru.userservice.configuration.database.entities.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository  extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findAllByUserDetailsId(Long userId);
}
