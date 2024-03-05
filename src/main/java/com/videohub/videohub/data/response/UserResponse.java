package com.videohub.videohub.data.response;

import com.videohub.videohub.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Represents a response containing user information.
 */
@Data  // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@Builder  // Lombok annotation to generate a builder pattern for object creation
public class UserResponse {

    /**
     * Represents the status of the user response.
     */
    private Boolean status;

    /**
     * Unique identifier for the user.
     */
    private String id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Password of the user.
     */
    private String password;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Set of roles assigned to the user.
     */
    private Set<Role> authorities;

    /**
     * Indicates whether the user is active.
     */
    private Integer active;
}
