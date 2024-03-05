package com.videohub.videohub.security.jwt;

import com.videohub.videohub.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;


/**
 * Filter class to intercept incoming requests and validate JWT tokens.
 * Once a valid token is found, it sets the user's authentication in the security context.
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    /**
     * Filters each incoming request to validate JWT token and set authentication.
     *
     * @param request     HttpServletRequest object representing the incoming request.
     * @param response    HttpServletResponse object representing the outgoing response.
     * @param filterChain FilterChain object to proceed with the request/response.
     * @throws ServletException If an exception occurs during the filter operation.
     * @throws IOException      If an I/O error occurs during the filter operation.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = getJwt(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Can Not set user auth -> Message
        }

        filterChain.doFilter(request, response);
    }


    /**
     * Retrieves JWT token from the request headers.
     *
     * @param request HttpServletRequest object representing the incoming request.
     * @return The JWT token extracted from the request headers, or null if not found.
     */
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
