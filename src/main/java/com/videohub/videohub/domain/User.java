package com.videohub.videohub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a user entity in the application.
 */
@Data
@Document(collection = "users")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    private String id;

    /**
     * The username of the user.
     */
    @NotBlank
    @Size(max = 20)
    private String username;

    /**
     * The email address of the user.
     */
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    /**
     * The password of the user.
     * Ignored during JSON serialization.
     */
    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;

    /**
     * The active status of the user.
     */
    private Integer active;

    /**
     * The roles/authorities assigned to the user.
     * Uses MongoDB's DBRef to establish a reference to Role entities.
     */
    @DBRef
    private Set<Role> authorities = new HashSet<>();

    /**
     * The videos uploaded by the user.
     * Uses MongoDB's DBRef to establish a reference to Video entities.
     */

    @DBRef
    private Set<Video> videos = new HashSet<>();

    /**
     * The date when the user account was created.
     */
    private Date created;

    /**
     * The date when the user account was last modified.
     */
    private Date modified;


    /**
     * Default constructor for the User class.
     */
    public User() {
    }


    /**
     * Constructor for the User class with parameters for username, email, and password.
     * @param username The username of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    /**
     * Constructor for the User class with parameters for username, password, email, and active status.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email address of the user.
     * @param active The active status of the user.
     */
    public User(String username, String password, String email, Integer active) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.created = new Date();
        this.modified = new Date();
    }
}
