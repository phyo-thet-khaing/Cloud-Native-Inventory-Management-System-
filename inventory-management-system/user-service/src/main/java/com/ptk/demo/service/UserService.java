package com.ptk.demo.service;

import java.util.List;

import org.jspecify.annotations.Nullable;

import com.ptk.demo.dto.request.AssignRoleRequest;
import com.ptk.demo.dto.request.CreateUserRequest;
import com.ptk.demo.dto.request.UpdateUserRequest;
import com.ptk.demo.dto.response.UserResponse;

public interface UserService {

	
		UserResponse createUser(CreateUserRequest request);
		
		UserResponse getUserById(Long id);
		
		List<UserResponse> getAllUsers();
		
		UserResponse updateUser(Long id, UpdateUserRequest request);
		
		void assignRole (Long userId,AssignRoleRequest request);
		
		void deleteUser(Long id);

		UserResponse getUserByUsername(String username);
	
}
