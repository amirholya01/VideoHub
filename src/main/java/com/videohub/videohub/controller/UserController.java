package com.videohub.videohub.controller;
import com.videohub.videohub.data.response.UserResponse;
import com.videohub.videohub.domain.User;
import com.videohub.videohub.repository.UserRepository;
import com.videohub.videohub.security.service.UserDetailsImpl;
import com.videohub.videohub.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@CrossOrigin(origins = "*", maxAge = 3600)  // Allowing requests from all origins and caching pre-flight response for 3600 seconds
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    // Endpoint to get list of all users (requires ADMIN role)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public List<User> users() {
        return userRepository.findAll();
    }

    // Endpoint to find user by ID (requires ADMIN role)
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public UserResponse findUserById(
            @PathVariable(value = "userId") String userId) {
        return userService.findUserById(userId);
    }


    // Endpoint to delete user by ID (requires ADMIN role)
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public void deletedUser(@PathVariable(value = "userId") String userId) {
        userRepository.deleteById(userId);
    }


    // Endpoint to find user by authentication token (requires ADMIN or USER role)
    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")  // Allowing users with ADMIN or USER role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDetailsImpl findUserByToken(Authentication authentication) {
        return userService.findUserByToken(authentication);
    }
}
