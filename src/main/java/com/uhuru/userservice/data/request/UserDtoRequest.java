package com.uhuru.userservice.data.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserDtoRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @Size(max = 50, message = "Middle name must be less than 50 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    public @NotBlank(message = "First name is required") @Size(max = 50, message = "First name must be less than 50 characters") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is required") @Size(max = 50, message = "First name must be less than 50 characters") String firstName) {
        this.firstName = firstName;
    }

    public @Size(max = 50, message = "Middle name must be less than 50 characters") String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(@Size(max = 50, message = "Middle name must be less than 50 characters") String middleName) {
        this.middleName = middleName;
    }

    public @NotBlank(message = "Last name is required") @Size(max = 50, message = "Last name must be less than 50 characters") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is required") @Size(max = 50, message = "Last name must be less than 50 characters") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "Date of birth is required") @Past(message = "Date of birth must be in the past") LocalDate getDob() {
        return dob;
    }

    public void setDob(@NotNull(message = "Date of birth is required") @Past(message = "Date of birth must be in the past") LocalDate dob) {
        this.dob = dob;
    }

    public @NotBlank(message = "Address is required") @Size(max = 255, message = "Address must be less than 255 characters") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is required") @Size(max = 255, message = "Address must be less than 255 characters") String address) {
        this.address = address;
    }

    public @NotBlank(message = "Gender is required") @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER") String getGender() {
        return gender;
    }

    public void setGender(@NotBlank(message = "Gender is required") @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER") String gender) {
        this.gender = gender;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 100, message = "Email must be less than 100 characters") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 100, message = "Email must be less than 100 characters") String email) {
        this.email = email;
    }
}
