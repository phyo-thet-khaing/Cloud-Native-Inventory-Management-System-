package com.ptk.demo.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ptk.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        System.out.println(
                "JWT FILTER: "
                        + request.getMethod()
                        + " "
                        + requestUri
        );

        // =========================================================
        // 1. Get Authorization header
        // =========================================================

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank()) {

            System.out.println(
                    "JWT FILTER: Authorization header missing"
            );

            filterChain.doFilter(request, response);
            return;
        }

        // =========================================================
        // 2. Check Bearer token
        // =========================================================

        if (!authHeader.startsWith("Bearer ")) {

            System.out.println(
                    "JWT FILTER: Invalid Authorization header"
            );

            filterChain.doFilter(request, response);
            return;
        }

        // =========================================================
        // 3. Extract token
        // =========================================================

        String token = authHeader.substring(7).trim();

        if (token.isBlank()) {

            System.out.println(
                    "JWT FILTER: Token is empty"
            );

            filterChain.doFilter(request, response);
            return;
        }

        try {

            // =====================================================
            // 4. Extract username from JWT
            // =====================================================

            String username =
                    jwtService.extractUsername(token);

            // =====================================================
            // 5. Extract role from JWT
            // =====================================================

            String role =
                    jwtService.extractRole(token);

            System.out.println(
                    "JWT USERNAME: " + username
            );

            System.out.println(
                    "JWT ROLE: " + role
            );

            // =====================================================
            // 6. Validate JWT
            // =====================================================

            boolean validToken =
                    username != null
                            && !username.isBlank()
                            && role != null
                            && !role.isBlank()
                            && jwtService.isTokenValid(
                                    token,
                                    username
                            );

            if (!validToken) {

                System.out.println(
                        "JWT FILTER: Invalid token"
                );

                filterChain.doFilter(request, response);
                return;
            }

            // =====================================================
            // 7. Check if authentication already exists
            // =====================================================

            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication() != null) {

                System.out.println(
                        "JWT FILTER: Authentication already exists"
                );

                filterChain.doFilter(request, response);
                return;
            }

            // =====================================================
            // 8. Create Spring Security authority
            // =====================================================

            role = role.trim();

            String authority;

            if (role.startsWith("ROLE_")) {
                authority = role;
            } else {
                authority = "ROLE_" + role;
            }

            System.out.println(
                    "SPRING AUTHORITY: " + authority
            );

            // =====================================================
            // 9. Create Authentication object
            // =====================================================

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(
                                            authority
                                    )
                            )
                    );

            // =====================================================
            // 10. Add request details
            // =====================================================

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // =====================================================
            // 11. Set authentication into SecurityContext
            // =====================================================

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println(
                    "JWT AUTHENTICATION SUCCESS"
            );

            System.out.println(
                    "AUTHORITIES: "
                            + authentication.getAuthorities()
            );

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR: " + e.getMessage()
            );

            SecurityContextHolder.clearContext();
        }

        // =========================================================
        // 12. Continue filter chain
        // =========================================================

        filterChain.doFilter(request, response);
    }
}