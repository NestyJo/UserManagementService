package com.uhuru.userservice.configuration.database.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleId userRoleId;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDetails userDetails;

    @ManyToOne
    @MapsId("role")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public UserRole() {
    }

    public UserRole(UserDetails userDetails, Role role) {
        this.userDetails = userDetails;
        this.role = role;
        this.userRoleId = new UserRoleId(userDetails.getId(), role.getId());
    }
}