package com.videohub.videohub.security.jwt;

import com.videohub.videohub.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${videoHub.app.jwtSecret}")
    private String jwtSecret;

    @Value("${videoHub.app.jwtExpirationMs}")
    private String jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                //.setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
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

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
