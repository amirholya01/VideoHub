package com.videohub.videohub.repository;

import com.videohub.videohub.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by their username.
     * @param username The username of the user.
     * @return An optional containing the user with the specified username, if found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the specified username exists.
     * @param username The username to check.
     * @return True if a user with the specified username exists, otherwise false.
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a user with the specified email exists.
     * @param email The email to check.
     * @return True if a user with the specified email exists, otherwise false.
     */
    Boolean existsByEmail(String email);
}
