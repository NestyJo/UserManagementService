package com.uhuru.userservice.service;


import com.uhuru.userservice.configuration.database.DatabaseRepository;
import com.uhuru.userservice.configuration.database.entities.*;
import com.uhuru.userservice.data.ApiResponse;
import com.uhuru.userservice.data.request.UserDtoRequest;
import com.uhuru.userservice.utility.LoggerService;
import com.uhuru.userservice.utility.ResponseUtil;
import com.uhuru.userservice.utility.UtilityService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterface {
    private final DatabaseRepository databaseRepository;
    private final UtilityService utilityService;
    private final LoggerService loggerService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(DatabaseRepository databaseRepository, UtilityService utilityService, LoggerService loggerService) {
        this.databaseRepository = databaseRepository;
        this.utilityService = utilityService;
        this.loggerService = loggerService;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Object>> createUser(UserDtoRequest user) {
        Optional<UserDetails> isUserExists = databaseRepository.userDetailsRepository.findByEmail(user.getEmail());
        if (isUserExists.isPresent()) {
            loggerService.log("User creation failed: Email {} already exists", user.getEmail());
            return ResponseUtil.error("User with the same email already exists", HttpStatus.CONFLICT);
        }

        UserDetails details = createUserPayload(user);
        databaseRepository.userDetailsRepository.saveAndFlush(details);


        Optional<Role> defaultRole = Optional.ofNullable(databaseRepository.roleRepository.findByName("User"));
        if (defaultRole.isEmpty()) {
            loggerService.log("Default role not found");
            return ResponseUtil.error("Default role not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        try {
            UserRole userRole = new UserRole(details, defaultRole.get());
            databaseRepository.userRoleRepository.save(userRole);
        } catch (Exception e) {
            loggerService.log("Error assigning default role to user: {}", e.getMessage());
            return ResponseUtil.error("Failed to assign default role to user", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (checkIfUserExits(details.getId())) {
            UserAccess access = createUserAccessPayload(details);
            databaseRepository.userAccessRepository.save(access);
            return ResponseUtil.success(true, "User created successfully", details);
        }

        loggerService.log("User created but not added to access table: {}", details.getEmail());
        return ResponseUtil.success(true, "User created but access not assigned", details);
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
        Optional<UserDetails> userOptional = databaseRepository.userDetailsRepository.findById(userId);
        if (userOptional.isPresent()) {

            UserDetails user = userOptional.get();
            return ResponseUtil.success(true, "User retrieved successfully", user);
        } else {
            return ResponseUtil.error("User not found with ID:" + userId, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> updateUser(Long userId, UserDtoRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserDetails>>> searchUsers(String firstName, String lastName, String email) {
        try {
            if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()) && (email == null || email.trim().isEmpty())) {
                ResponseUtil.error("At least one search parameter is required", HttpStatus.BAD_REQUEST);
            }
            List<UserDetails> users = databaseRepository.getUserDetailsRepository().findByFirstNameContainingAndLastNameContainingAndEmailContaining(firstName != null ? firstName : "", lastName != null ? lastName : "", email != null ? email : "");

            if (users.isEmpty()) {
                return ResponseUtil.error("No users found", HttpStatus.NOT_FOUND);
            }

            return ResponseUtil.success(true, "Users retrieved successfully", users);

        } catch (Exception e) {
            loggerService.log("Failed to retrieve User: {}", e.getMessage());
            return ResponseUtil.error("Failed to retrieve users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Object>> enableUser(Long userId) {
        if (!checkIfUserExits(userId)) {
            return ResponseUtil.error("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
        }

        if (!enableUserUpdate(userId)) {
            return ResponseUtil.error("Failed to enable user. Please try again.", HttpStatus.CONFLICT);
        }

        return ResponseUtil.success(true, "User is enabled", "");
    }

    @Transactional
    public ResponseEntity<ApiResponse<Object>> disableUser(Long userId) {
        if (!checkIfUserExits(userId)) {
            return ResponseUtil.error("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
        }

        if (!disableUserUpdate(userId)) {
            return ResponseUtil.error("Failed to disable user. Please try again.", HttpStatus.CONFLICT);
        }

        return ResponseUtil.success(true, "User has been Disabled", "");
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> deleteUser(Long userId) {
        return null;
    }

    private boolean checkIfUserExits(Long userId) {
        loggerService.log("Here is user Id we a looking for {}: " + userId);
        Optional<UserDetails> isUserExists = databaseRepository.userDetailsRepository.findById(userId);
        return isUserExists.isPresent();
    }

    private UserAccess createUserAccessPayload(UserDetails userDetails) {

        String password = changeAFirstLetterCapitalAndTheRestToSmall(userDetails.getLastName());
        loggerService.log(password, "password: {}");

        String hashedPassword = hashPassword(password);
        loggerService.log(hashedPassword, "password: {}");

        UserAccess userAccess = new UserAccess();
        userAccess.setUserDetails(userDetails);
        userAccess.setUsername(userDetails.getUserNo());
        userAccess.setPassword(hashedPassword);
        userAccess.setEnabled(false);
        userAccess.setLastLogin(null);
        userAccess.setSessionTime(null);
        userAccess.setOneTimePassword(null);

        return userAccess;
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

    private String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    private String changeAFirstLetterCapitalAndTheRestToSmall(String defaultPassword) {
        return defaultPassword.substring(0, 1).toUpperCase() + defaultPassword.substring(1).toLowerCase();
    }

    private boolean enableUserUpdate(Long id) {
        int updatedRows = databaseRepository.getUserDetailsRepository().enableUser(id);
        return updatedRows > 0;
    }

    private boolean disableUserUpdate(Long id) {
        int updatedRows = databaseRepository.getUserDetailsRepository().enableUser(id);
        return updatedRows > 0;
    }
}