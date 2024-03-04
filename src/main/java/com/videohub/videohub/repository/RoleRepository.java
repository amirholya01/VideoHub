package com.videohub.videohub.repository;

import com.videohub.videohub.domain.Role;
import com.videohub.videohub.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for managing Role entities in the database.
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * Finds a role by its name.
     * @param name The name of the role.
     * @return An optional containing the role with the specified name, if found.
     */
    Optional<Role> findByName(ERole name);
}
