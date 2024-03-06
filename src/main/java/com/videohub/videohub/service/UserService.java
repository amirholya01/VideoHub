package com.videohub.videohub.service;

import com.videohub.videohub.data.request.SignupRequest;
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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User updateUser(String userId, SignupRequest signUpForm) {
        Optional<User> userDate = userRepository.findById(userId);
        if (userDate.isPresent()) {
            if (signUpForm.getUsername() != null)
                userDate.get().setUsername(signUpForm.getUsername());
            if (signUpForm.getEmail() != null)
                userDate.get().setEmail(signUpForm.getEmail());
            if (signUpForm.getActive() != null)
                userDate.get().setActive(signUpForm.getActive());

            if (signUpForm.getPassword() != null)
                userDate.get().setPassword(
                        encoder.encode(signUpForm.getPassword()));
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
        Set<String> roles = signUpForm.getRoles();
        Set<Role> roleSet = new HashSet<>();
        for (String role:roles) {
            Role roleData = roleRepository.findByName(ERole.valueOf(role)).get();
            roleSet.add(roleData);
        }
        userDate.get().setAuthorities(roleSet);

        return userRepository.save(userDate.get());
    }
}
