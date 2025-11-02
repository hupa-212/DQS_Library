package com.api.bookstore.library_backend.service;

import java.util.List;
import java.util.stream.Collectors;
import com.api.bookstore.library_backend.dto.response.RoleResponse;
import com.api.bookstore.library_backend.model.Role;
import com.api.bookstore.library_backend.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;

    public RoleResponse createRole(String name, String description) {
        Role role = Role.builder()
                .name(name)
                .description(description)
                .isDeleted(false)
                .build();
        Role savedRole = roleRepository.save(role);
        return toResponse(savedRole);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .filter(r -> !r.getIsDeleted())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        if (role.getIsDeleted()) {
            throw new RuntimeException("Role not found with id: " + id);
        }
        return toResponse(role);
    }

    private RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}