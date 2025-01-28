package com.uhuru.userservice.configuration.database.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean isEnabled = true;

    private Timestamp lastLogin;

    private Integer sessionTime;

    private String oneTimePassword;
}
