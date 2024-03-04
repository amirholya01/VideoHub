package com.videohub.videohub.enums;

/**
 * Enum representing different roles in the application.
 * Users can have one of the following roles: USER, MODERATOR, ADMIN.
 */

public enum ERole {

    /**
     * Represents a regular user role.
     */
    ROLE_USER,
    /**
     * Represents a moderator role with additional privileges.
     */
    ROLE_MODERATOR,
    /**
     * Represents an administrator role with full access and privileges.
     */
    ROLE_ADMIN
}
