package com.videohub.videohub.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.videohub.videohub.domain.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



/**
 * Custom implementation of UserDetails interface to represent user details retrieved during authentication.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Integer active;

    private Collection <? extends GrantedAuthority> authorities;


    /**
     * Constructor to create UserDetailsImpl object with user details.
     */
    public UserDetailsImpl(String id,
                           String username,
                           String email,
                           String password,
                           Collection<? extends GrantedAuthority> authorities,
                           Integer active
                           ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.active = active;

    }

    /**
     * Static method to build UserDetailsImpl object from User domain object.
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getActive()
        );
    }

    // Getters for UserDetailsImpl fields

    /**
     * Returns the unique identifier of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the email address of the user.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Returns the username of the user.
     */
    @Override
    public String getUsername() {
        return username;
    }


    /**
     * Returns the password of the user.
     */
    @Override
    public String getPassword() {
        return password;
    }


    /**
     * Returns the authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Methods for account status checks

    /**
     * Returns true if the user account is not expired.
     */
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * Returns true if the user account is not locked.
     */
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * Returns true if the user credentials are not expired.
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Returns true if the user account is enabled.
     */
    public boolean isEnabled() {
        return true;
    }


    // Overridden equals and hashCode methods

    /**
     * Indicates whether some other object is "equal to" this one.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) object;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(active, that.active) &&
                Objects.equals(authorities, that.authorities);
    }


    /**
     * Returns a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, active, authorities);
    }
}
