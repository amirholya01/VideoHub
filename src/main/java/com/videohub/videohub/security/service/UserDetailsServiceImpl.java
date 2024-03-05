package com.videohub.videohub.security.service;

import com.videohub.videohub.domain.User;
import com.videohub.videohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service class to load user details by username/email for authentication.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
     UserRepository userRepository;


    /**
     * Loads user details by username/email for authentication.
     *
     * @param username The username or email of the user.
     * @return UserDetails object containing user details.
     * @throws UsernameNotFoundException If user with given username/email is not found.
     */
    @Override
    @Transactional
    // username or email
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Find user by username or email from the UserRepository
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email" + username));

        // Build UserDetailsImpl object from the retrieved user
        return UserDetailsImpl.build(user);
    }
}
