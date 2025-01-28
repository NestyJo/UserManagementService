package com.uhuru.userservice.configuration.database.repository;

import com.uhuru.userservice.configuration.database.entities.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {
}
