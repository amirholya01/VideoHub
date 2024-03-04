package com.videohub.videohub.repository;

import com.videohub.videohub.domain.Role;
import com.videohub.videohub.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
