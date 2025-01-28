package com.uhuru.userservice.configuration.database.repository;

import com.uhuru.userservice.configuration.database.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
}
