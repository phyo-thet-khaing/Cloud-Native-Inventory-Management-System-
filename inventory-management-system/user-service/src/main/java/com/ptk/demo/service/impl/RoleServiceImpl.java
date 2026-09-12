package com.ptk.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptk.demo.dto.response.RoleResponse;
import com.ptk.demo.exception.RoleNotFoundException;
import com.ptk.demo.mapper.RoleMapper;
import com.ptk.demo.model.Role;
import com.ptk.demo.repository.RoleRepository;
import com.ptk.demo.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role not found with id: " + id));

        return roleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }
}