package com.videohub.videohub.security.jwt;

import com.videohub.videohub.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Component responsible for JWT (JSON Web Token) generation, validation, and retrieval of user information from the token.
 */
@Component
public class JwtProvider {

    @Value("${videoHub.app.jwtSecret}")
    private String jwtSecret;

    @Value("${videoHub.app.jwtExpirationMs}")
    private String jwtExpiration;


    /**
     * Generates a JWT token based on the provided authentication details.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT token.
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))  // Set the subject as the username
                .setIssuedAt(new Date())  // Set the token issue time
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))  // Set the token expiration time
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  // Sign the token with the secret key using HS512 algorithm
                .compact();  // Compact and return the token as a string
    }


    /**
     * Validates the provided JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Parse and validate the token's signature
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            // Invalid JWT signature -> Message ...
        } catch (ExpiredJwtException e){
            // Expired JWT token -> Message ...
        } catch (UnsupportedJwtException e){
            // Unsupported Jwt Token -> Message ...
        } catch (IllegalArgumentException e) {
            // JWT claims string is empty -> Message ...
        }
        return false;
    }


    /**
     * Retrieves the username from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)  // Set the secret key for parsing
                .parseClaimsJws(token) // Parse the token
                .getBody().getSubject();  // Get the subject (username) from the token's body
    }
}
