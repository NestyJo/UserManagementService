package com.uhuru.userservice.configuration.database.repository;

import com.uhuru.userservice.configuration.database.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
    Optional<UserDetails> findByEmail(String email);
    List<UserDetails> findByFirstNameContainingAndLastNameContainingAndEmailContaining(String firstName, String lastName, String email);
}
