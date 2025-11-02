package com.api.bookstore.library_backend.controller;

import java.util.List;
import com.api.bookstore.library_backend.dto.response.ApiResponse;
import com.api.bookstore.library_backend.dto.response.PermissionResponse;
import com.api.bookstore.library_backend.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)    
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestParam String name,
            @RequestParam(required = false) String description) {
        PermissionResponse permissionResponse = permissionService.createPermission(name, description);
        ApiResponse<PermissionResponse> response = ApiResponse.<PermissionResponse>builder()
                .message("Permission created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(permissionResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        List<PermissionResponse> permissions = permissionService.getAllPermissions();
        ApiResponse<List<PermissionResponse>> response = ApiResponse.<List<PermissionResponse>>builder()
                .message("Permissions retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(permissions)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
        PermissionResponse permission = permissionService.getPermissionById(id);
        ApiResponse<PermissionResponse> response = ApiResponse.<PermissionResponse>builder()
                .message("Permission retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(permission)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}