package com.videohub.videohub.data.response;

import org.springframework.security.core.GrantedAuthority;
import lombok.Data;

import java.util.Collection;

@Data  // @Data annotation from Lombok automatically generates getters, setters, equals, hashCode, and toString methods
public class JwtResponse {

    private String token;  // Private fields to store JWT token, token type, username, authorities, result, id, and active status
    private String type = "Bearer";  // Default token type is "Bearer"
    private String username;
    private Collection<? extends GrantedAuthority> authorities;  // Collection of authorities granted to the user
    private String result;  // Result of the response (if any)
    private String id;  // Identifier associated with the response (if any)
    private Integer active;  // Active status of the user


    // Constructor to initialize token, username, authorities, and active status
    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities, Integer active) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.active = active;
    }


    // Constructor to initialize token, username, authorities, result, id, and active status
    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities, String result, String id, Integer active) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.result = result;
        this.id = id;
        this.active = active;
    }



    // Constructor to initialize token, token type, username, authorities, result, id, and active status
    public JwtResponse(String token, String type, String username, Collection<? extends GrantedAuthority> authorities, String result, String id, Integer active) {
        this.token = token;
        this.type = type;
        this.username = username;
        this.authorities = authorities;
        this.result = result;
        this.id = id;
        this.active = active;
    }
}
