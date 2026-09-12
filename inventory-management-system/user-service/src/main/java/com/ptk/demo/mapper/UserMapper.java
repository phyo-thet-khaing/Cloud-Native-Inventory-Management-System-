package com.ptk.demo.mapper;

import org.springframework.stereotype.Component;

import com.ptk.demo.dto.request.CreateUserRequest;
import com.ptk.demo.dto.request.UpdateUserRequest;
import com.ptk.demo.dto.response.UserResponse;
import com.ptk.demo.model.Role;
import com.ptk.demo.model.User;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toEntity(
            CreateUserRequest request,
            Role role) {

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(role)
                .mustChangePassword(false)
                .build();
    }

    public void updateEntity(
            User user,
            UpdateUserRequest request) {

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
    }

    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .mustChangePassword(user.getMustChangePassword())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .role(roleMapper.toResponse(user.getRole()))
                .build();
    }
}