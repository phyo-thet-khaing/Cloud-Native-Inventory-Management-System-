
package com.ptk.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ptk.demo.dto.request.ChangePasswordRequest;
import com.ptk.demo.dto.request.LoginRequest;
import com.ptk.demo.dto.request.RegisterRequest;
import com.ptk.demo.dto.response.LoginResponse;
import com.ptk.demo.dto.response.RoleResponse;
import com.ptk.demo.dto.response.UserResponse;
import com.ptk.demo.exception.AuthenticationException;
import com.ptk.demo.model.Role;
import com.ptk.demo.model.User;
import com.ptk.demo.repository.RoleRepository;
import com.ptk.demo.repository.UserRepository;
import com.ptk.demo.service.AuthService;
import com.ptk.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
    		
    	
    		//Find User
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new AuthenticationException(
                                "Invalid username or password"
                        )
                );
        
        //Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new AuthenticationException(
                    "Invalid username or password"
            );
        }
        
        
        //Check role
        if (user.getRole() == null) {
            throw new AuthenticationException(
                    "User role not found"
            );
        }
        
        //Check account status
        if(user.getStatus()== null) {
        		
        		throw new AuthenticationException(
        				"User status not Found");
        }
        if(!user.getStatus().name().equals("ACTIVE")) {
        		
        		throw new AuthenticationException(
        				"User account is not active");    
        		}
        
        //Update last login time
        user.setLastLoginAt(LocalDateTime.now());
        
        userRepository.save(user);
        
        
        // Get Role
        String role = user.getRole().getName();
        
        
        //Generate JWT
        String token = jwtService.generateToken(
                user.getUsername(),
                role
        );
        
        
        //Return login response
        return new LoginResponse(
                token,
                user.getUsername(),
                user.getMustChangePassword(),
                List.of(role)
        );
    }

    @Override
    public UserResponse setupAdmin(RegisterRequest request) {

        // Check whether ADMIN already exists
        boolean adminExists = userRepository
                .existsByRole_Name("ADMIN");

        if (adminExists) {
            throw new AuthenticationException(
                    "Admin already exists"
            );
        }

        // Check username
        if (userRepository
                .findByUsername(request.getUsername())
                .isPresent()) {

            throw new AuthenticationException(
                    "Username already exists"
            );
        }

        // Create admin
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setPhone(request.getPhone());

        // Get ADMIN role
        Role adminRole = roleRepository
                .findByName("ADMIN")
                .orElseThrow(() ->
                        new AuthenticationException(
                                "ADMIN role not found"
                        )
                );

        user.setRole(adminRole);

        // Save admin
        User savedUser = userRepository.save(user);

        // Return response
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getStatus(),
                savedUser.getMustChangePassword(),
                savedUser.getLastLoginAt(),
                savedUser.getCreatedAt(),
                new RoleResponse(
                        savedUser.getRole().getId(),
                        savedUser.getRole().getName()
                )
        );
    }
    
    // ========================================================= 
    // CHANGE PASSWORD 
    // =========================================================

	@Override
	public void changePassword(ChangePasswordRequest request) {
		// TODO Auto-generated method stub
		
		
		//Find currently logged in user
		String username = jwtService.getCurrentUsername();
		
		if(username == null || username.isBlank()) {
			throw new AuthenticationException(
					"User is not authentication");
		}
		
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(()->
						new AuthenticationException(
								"User not found"));
		
		//check current password
		if(!passwordEncoder.matches(
				request.getCurrentPassword(),
				user.getPassword())) {
			
			throw new AuthenticationException(
					"Current password is incorrect");
			
		}
		
		//check new password and confirm password
		if(!request.getNewPassword().equals(
				request.getConfirmPassword()
				)) {
			throw new AuthenticationException(
					"New password and confirm password do not match");
		}
		
		//Prevent using the same password
		if(passwordEncoder.matches(request.getNewPassword(),
				user.getPassword())) {
			
			throw new AuthenticationException(
					"New password  must be different from current password");
		}
	
		
		//Encode new password
		user.setPassword(
				passwordEncoder.encode(
						request.getNewPassword()));
		
		//Password has been changed
		user.setMustChangePassword(false);
		
		userRepository.save(user);
		
	};
	
		
		
	
	
	
}

