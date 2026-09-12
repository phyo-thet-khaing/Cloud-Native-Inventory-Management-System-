package com.ptk.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                // ==========================================
                // CSRF
                // ==========================================
                .csrf(csrf -> csrf.disable())

                // ==========================================
                // Stateless JWT authentication
                // ==========================================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ==========================================
                // Authorization
                // ==========================================
                .authorizeHttpRequests(auth -> auth

                        // ----------------------------------
                        // Public authentication endpoints
                        // ----------------------------------
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/setup-admin"
                        ).permitAll()

                        // ----------------------------------
                        // Swagger
                        // ----------------------------------
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // ----------------------------------
                        // Actuator
                        // ----------------------------------
                        .requestMatchers(
                                "/actuator/**"
                        ).permitAll()

                        // ----------------------------------
                        // Admin only
                        // ----------------------------------
                        .requestMatchers(
                                "/api/users/**",
                                "/api/roles/**"
                        ).hasRole("ADMIN")

                        // ----------------------------------
                        // All other endpoints
                        // JWT required
                        // ----------------------------------
                        .anyRequest().authenticated()
                )

                // ==========================================
                // JWT Filter
                // ==========================================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}