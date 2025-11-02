package com.api.bookstore.library_backend.service;

import java.util.List;
import java.util.stream.Collectors;
import com.api.bookstore.library_backend.dto.response.PermissionResponse;
import com.api.bookstore.library_backend.model.Permission;
import com.api.bookstore.library_backend.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor 
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;

    public PermissionResponse createPermission(String name, String description) {
        Permission permission = Permission.builder()
                .name(name)
                .description(description)
                .isDeleted(false)
                .build();
        Permission savedPermission = permissionRepository.save(permission);
        return toResponse(savedPermission);
    }

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .filter(p -> !p.getIsDeleted())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
        if (permission.getIsDeleted()) {
            throw new RuntimeException("Permission not found with id: " + id);
        }
        return toResponse(permission);
    }

    private PermissionResponse toResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}