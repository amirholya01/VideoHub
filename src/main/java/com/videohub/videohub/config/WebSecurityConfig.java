package com.videohub.videohub.config;
import com.videohub.videohub.security.service.UserDetailsServiceImpl;
import com.videohub.videohub.security.jwt.JwtAuthEntryPoint;
import com.videohub.videohub.security.jwt.JwtAuthTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    /*
     * Autowired instance of UserDetailsServiceImpl to be used for user details retrieval.
     */
    @Autowired
     UserDetailsServiceImpl userDetailsService;

    /*
     * Autowired instance of JwtAuthEntryPoint to handle unauthorized requests.
     */
    @Autowired
    private JwtAuthEntryPoint  unauthorizedHandler;


    /*
     * Creates and returns an instance of JwtAuthTokenFilter.
     */
    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }


    /*
     * Creates and returns an instance of BCryptPasswordEncoder for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*
     * Creates and returns an instance of DaoAuthenticationProvider.
     * Configures it with UserDetailsServiceImpl and PasswordEncoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    /*
     * Creates and returns an instance of AuthenticationManager.
     * Utilizes AuthenticationConfiguration to obtain authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    /*
     * Configures the security filter chain for HTTP requests.
     * Disables CSRF and CORS protection, sets session creation policy to STATELESS,
     * defines authorization rules, adds authentication provider and JWT authentication filter.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
