package com.uhuru.userservice.service;


import com.uhuru.userservice.configuration.database.DatabaseRepository;
import com.uhuru.userservice.configuration.database.entities.UserDetails;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.UserDtoRequest;
import com.uhuru.userservice.utility.LoggerService;
import com.uhuru.userservice.utility.ResponseUtil;
import com.uhuru.userservice.utility.UtilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterface {
    private final DatabaseRepository databaseRepository;
    private final UtilityService utilityService;
    private final LoggerService loggerService;

    public UserService(DatabaseRepository databaseRepository, UtilityService utilityService, LoggerService loggerService) {
        this.databaseRepository = databaseRepository;
        this.utilityService = utilityService;
        this.loggerService = loggerService;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> createUser(UserDtoRequest user) {
        try {
            Optional<UserDetails> isUserExists = databaseRepository.userDetailsRepository.findByEmail(user.getEmail());
            if (isUserExists.isPresent()) {
                return ResponseUtil.error("User with the same email already exists", HttpStatus.CONFLICT);
            }

            UserDetails details = createUserPayload(user);
            databaseRepository.userDetailsRepository.save(details);

            loggerService.log("User created successfully: {}", details.getEmail());
            return ResponseUtil.success(true, "user created", details);

        } catch (Exception e) {
            loggerService.log("Failed to create user: {}", e.getMessage());
            return ResponseUtil.error("Failed to create role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserDetails>>> getAllUsers() {
        try {
            List<UserDetails> users = databaseRepository.userDetailsRepository.findAll();
            loggerService.log("Retrieved users: {}", String.valueOf(users.size()));
            return ResponseUtil.success(true, "Users retrieved successfully", users);

        } catch (Exception e) {
            loggerService.log("Failed to retrieve User: {}", e.getMessage());
            return ResponseUtil.error("Failed to retrieve users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UserDetails>> getUserById(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> updateUser(Long userId, UserDtoRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> deleteUser(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserDetails>>> searchUsers(String firstName, String lastName, String email) {
        return null;
    }


    private UserDetails createUserPayload(UserDtoRequest request) {
        UserDetails userDetails = new UserDetails();

        userDetails.setFirstName(request.getFirstName());
        userDetails.setMiddleName(request.getMiddleName());
        userDetails.setLastName(request.getLastName());
        userDetails.setDob(request.getDob());
        userDetails.setAddress(request.getAddress());
        userDetails.setGender(request.getGender());
        userDetails.setEmail(request.getEmail());
        userDetails.setInitials(generateInitials(request.getFirstName(), request.getMiddleName(), request.getLastName()));
        userDetails.setUserNo(generateUserNo(userDetails.getInitials()));

        return userDetails;
    }

    private String generateInitials(String firstName, String middleName, String lastName) {
        StringBuilder initials = new StringBuilder();

        if (firstName != null && !firstName.isEmpty()) {
            initials.append(Character.toUpperCase(firstName.charAt(0)));
        }

        if (middleName != null && !middleName.isEmpty()) {
            initials.append(Character.toUpperCase(middleName.charAt(0)));
        }

        if (lastName != null && !lastName.isEmpty()) {
            initials.append(Character.toUpperCase(lastName.charAt(0)));
        }
        return initials.toString();
    }

    private String generateUserNo(String Initials) {
        return utilityService.getStringUtility().generateId(Initials);
    }
}
