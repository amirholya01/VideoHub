package com.videohub.videohub.controller;


import com.videohub.videohub.data.request.LoginRequest;
import com.videohub.videohub.data.request.SignupRequest;
import com.videohub.videohub.data.response.JwtResponse;
import com.videohub.videohub.data.response.ResponseMessage;
import com.videohub.videohub.domain.Role;
import com.videohub.videohub.domain.User;
import com.videohub.videohub.enums.ERole;
import com.videohub.videohub.repository.RoleRepository;
import com.videohub.videohub.repository.UserRepository;
import com.videohub.videohub.security.jwt.JwtProvider;
import com.videohub.videohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600) // Allowing requests from all origins and caching pre-flight response for 3600 seconds
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Autowiring repositories, services, authentication manager, password encoder, and JWT provider
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;



    // Endpoint for user registration


    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<?> registerUSer(@RequestBody SignupRequest signupRequest) {

        // Check if username is empty or null, if so, update the existing user with the new details
        if (signupRequest.getUsername().isEmpty() || signupRequest.getUsername() == null) {
            Optional<User> user = userRepository.findByUsername(signupRequest.getUsername());
            user.ifPresent(value -> userService.updateUser(value.getId(), signupRequest));
            return new ResponseEntity<>(
                    new ResponseMessage("User " + signupRequest.getUsername() + " is update successfully!"),
                    HttpStatus.OK);
        }

        // Check if username or email already exists, if so, update the existing user with the new details
        if (userRepository.existsByUsername(signupRequest.getUsername())
                || userRepository.existsByEmail(signupRequest.getEmail())) {
            Optional<User> user = userRepository.findByUsername(signupRequest.getUsername());
            user.ifPresent(value -> userService.updateUser(value.getId(), signupRequest));
            return new ResponseEntity<>(
                    new ResponseMessage("User " + signupRequest.getUsername() + " is update successfully!"),
                    HttpStatus.OK);
        }

        // Creating new User object with provided details
        User user = new User(
                signupRequest.getUsername(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail(),
                signupRequest.getActive());

        // Setting roles for the user
        Set<Role> roles = new HashSet<>();
        if (signupRequest.getRoles() != null) {
            Set<String> strRoles = signupRequest.getRoles(); // Roles requested by the user
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role pmRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(pmRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

                        roles.add(userRole);
                }
            });
        } else {

            // If no roles specified, assign ROLE_USER by default
            Role userRole =
                    roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

            roles.add(userRole);
        }

        // Setting roles and saving user to the database
        user.setAuthorities(roles);
        userRepository.save(user);

        // Returning success response
        return new ResponseEntity<>(
                new ResponseMessage("User " + signupRequest.getUsername() + " is registered successfully!"),
                HttpStatus.OK);
    }




    // Endpoint for user authentication


    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Authenticating user using provided credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Setting authentication in SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generating JWT token
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        // Initializing result to "error" by default
        String result = "error";
        // If JWT token is generated successfully, change result to "success"
        if(!jwt.isEmpty()){
            result = "success";
        }

        // Retrieving user id and checking if user is active or not

        String id = null;

        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if(user.isPresent()){
            id = user.get().getId();
            if(user.get().getActive() == 2){
                result = "notActive";
            }
        }
        // Returning JWT response along with username, authorities, result, id, and active status

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), result, id, user.get().getActive()));
    }



}
