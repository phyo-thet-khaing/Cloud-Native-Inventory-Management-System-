package com.ptk.demo.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ptk.demo.dto.request.AssignRoleRequest;
import com.ptk.demo.dto.request.CreateUserRequest;
import com.ptk.demo.dto.request.UpdateUserRequest;
import com.ptk.demo.dto.response.UserResponse;
import com.ptk.demo.exception.RoleNotFoundException;
import com.ptk.demo.exception.UserNotFoundException;
import com.ptk.demo.mapper.UserMapper;
import com.ptk.demo.model.Role;
import com.ptk.demo.model.User;
import com.ptk.demo.repository.RoleRepository;
import com.ptk.demo.repository.UserRepository;
import com.ptk.demo.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role not found with id: "
                                        + request.getRoleId()));

        User user = userMapper.toEntity(request, role);

        System.out.println("REQUEST PASSWORD: "
                + request.getPassword());

        System.out.println("BEFORE ENCODE: "
                + user.getPassword());

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        System.out.println("ENCODED PASSWORD: "
                + encodedPassword);

        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        System.out.println("DATABASE PASSWORD: "
                + savedUser.getPassword());

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        // Update normal user fields
        userMapper.updateEntity(user, request);

        // Update role if provided
        if (request.getRoleId() != null) {

            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() ->
                            new RoleNotFoundException(
                                    "Role not found with id: "
                                            + request.getRoleId()));

            user.setRole(role);
        }

        // Encode new password if provided
        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void assignRole(
            Long userId,
            AssignRoleRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role not found with id: "
                                        + request.getRoleId()));

        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with username: "
                                        + username));

        return userMapper.toResponse(user);
    }
}

