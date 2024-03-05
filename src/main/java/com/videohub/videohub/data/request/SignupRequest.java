package com.videohub.videohub.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * Represents a signup request data structure.
 */

@Data  // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor  // Lombok annotation to generate a constructor with all fields
public class SignupRequest {

    /**
     * Unique identifier for the user.
     */
    private String id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Password of the user.
     */
    private String password;

    /**
     * Indicates whether the user is active.
     * 1: Active
     * 2: Inactive
     */
    private Integer active; // 1 - 2

    /**
     * Set of roles assigned to the user.
     */
    private Set<String> roles;
}
