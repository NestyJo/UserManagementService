package com.uhuru.userservice.configuration.database.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_access")
public class UserAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails userDetails;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isEnabled = false;

    private Timestamp lastLogin;

    private Integer sessionTime;

    private String oneTimePassword;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public UserDetails getUserDetails() {
//        return userDetails;
//    }
//
//    public void setUserDetails(UserDetails userDetails) {
//        this.userDetails = userDetails;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        isEnabled = enabled;
//    }
//
//    public Timestamp getLastLogin() {
//        return lastLogin;
//    }
//
//    public void setLastLogin(Timestamp lastLogin) {
//        this.lastLogin = lastLogin;
//    }
//
//    public Integer getSessionTime() {
//        return sessionTime;
//    }
//
//    public void setSessionTime(Integer sessionTime) {
//        this.sessionTime = sessionTime;
//    }
//
//    public String getOneTimePassword() {
//        return oneTimePassword;
//    }
//
//    public void setOneTimePassword(String oneTimePassword) {
//        this.oneTimePassword = oneTimePassword;
//    }
}
