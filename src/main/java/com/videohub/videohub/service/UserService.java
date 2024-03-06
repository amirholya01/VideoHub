package com.videohub.videohub.service;

import com.videohub.videohub.data.request.SignupRequest;
import com.videohub.videohub.data.response.UserResponse;
import com.videohub.videohub.domain.Role;
import com.videohub.videohub.domain.User;
import com.videohub.videohub.enums.ERole;
import com.videohub.videohub.repository.RoleRepository;
import com.videohub.videohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;


    /**
     * Updates the user details.
     *
     * @param userId     ID of the user to be updated
     * @param signUpForm SignupRequest containing new user details
     * @return Updated User object
     */
    public User updateUser(String userId, SignupRequest signUpForm) {
        Optional<User> userDate = userRepository.findById(userId);
        if (userDate.isPresent()) {
            // Update user details if provided
            if (signUpForm.getUsername() != null)
                userDate.get().setUsername(signUpForm.getUsername());
            if (signUpForm.getEmail() != null)
                userDate.get().setEmail(signUpForm.getEmail());
            if (signUpForm.getActive() != null)
                userDate.get().setActive(signUpForm.getActive());
            // Update password if provided
            if (signUpForm.getPassword() != null)
                userDate.get().setPassword(
                        encoder.encode(signUpForm.getPassword()));
            // Update user roles if provided
            if (signUpForm.getRoles() != null) {
                Set<Role> authorities = new HashSet<>();
                for (String roleString : signUpForm.getRoles()) {
                    Role role = new Role();
                    role.setName(ERole.valueOf(roleString));
                    authorities.add(role);
                }
                userDate.get().setAuthorities(authorities);
            }
        }
        // Fetch and set roles for the user
        Set<String> roles = signUpForm.getRoles();
        Set<Role> roleSet = new HashSet<>();
        for (String role:roles) {
            Role roleData = roleRepository.findByName(ERole.valueOf(role)).get();
            roleSet.add(roleData);
        }
        userDate.get().setAuthorities(roleSet);
        // Save and return updated user
        return userRepository.save(userDate.get());
    }


    /**
     * Finds a user by ID.
     *
     * @param userId ID of the user to find
     * @return UserResponse containing user details if found, otherwise returns status false
     */
    public UserResponse findUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // Return status false if user not found
            return UserResponse.builder().status(Boolean.FALSE).build();
        }
        // Return UserResponse with user details
        return UserResponse.builder()
                .status(Boolean.TRUE)
                .id(user.get().getId())
                .username(user.get().getUsername())
                .email(user.get().getEmail())
                .authorities(user.get().getAuthorities())
                .active(user.get().getActive())
                .build();
    }


}
