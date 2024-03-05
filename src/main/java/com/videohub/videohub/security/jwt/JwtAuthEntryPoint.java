package com.videohub.videohub.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * Component to handle unauthorized requests.
 * It implements the AuthenticationEntryPoint interface to provide custom handling
 * when an unauthenticated user attempts to access a protected resource.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {


    /**
     * Method called when an unauthenticated user attempts to access a protected resource.
     * It sends an HTTP response with status code 401 (Unauthorized) and an error message.
     *
     * @param request       HttpServletRequest object representing the incoming request.
     * @param response      HttpServletResponse object representing the outgoing response.
     * @param authException The exception that caused the authentication failure.
     * @throws IOException      If an I/O error occurs while sending the response.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {

        // Send an HTTP response with status code 401 (Unauthorized) and an error message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
    }
}
