package com.uhuru.userservice.configuration.database.repository;

import com.uhuru.userservice.configuration.database.entities.UserDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
    Optional<UserDetails> findByEmail(String email);
    List<UserDetails> findByFirstNameContainingAndLastNameContainingAndEmailContaining(String firstName, String lastName, String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserDetails u SET u.enableTo = TRUE WHERE u.id = :userId")
    int enableUser(@Param("userId") Long userId);
}
