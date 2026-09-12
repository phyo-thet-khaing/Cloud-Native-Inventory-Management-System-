
package com.ptk.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptk.demo.dto.request.ChangePasswordRequest;
import com.ptk.demo.dto.request.LoginRequest;
import com.ptk.demo.dto.request.RegisterRequest;
import com.ptk.demo.dto.response.LoginResponse;
import com.ptk.demo.dto.response.UserResponse;
import com.ptk.demo.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
    name = "Authentication",
    description = "APIs for user authentication"
)
public class AuthController {

    private final AuthService authService;

    // =========================================================
    // LOGIN
    // =========================================================

    @Operation(
        summary = "User Login",
        description = "Authenticate user and return JWT token"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Login successful"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Invalid username or password"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // CHANGE PASSWORD
    // =========================================================

    @Operation(
        summary = "Change Password",
        description = "Change password for authenticated user"
    )
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponse(
        responseCode = "200",
        description = "Password changed successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid password information"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Authentication required"
    )
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        authService.changePassword(request);

        return ResponseEntity.ok(
                "Password changed successfully"
        );
    }

    // =========================================================
    // SETUP ADMIN
    // =========================================================

    @Operation(
        summary = "Setup Admin",
        description = "Create the initial admin user"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Admin created successfully"
    )
    @PostMapping("/setup-admin")
    public ResponseEntity<UserResponse> setupAdmin(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response =
                authService.setupAdmin(request);

        return ResponseEntity.ok(response);
    }
}

