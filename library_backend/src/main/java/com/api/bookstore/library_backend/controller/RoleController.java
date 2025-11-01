package com.api.bookstore.library_backend.controller;

import java.util.List;
import com.api.bookstore.library_backend.dto.response.ApiResponse;
import com.api.bookstore.library_backend.dto.response.RoleResponse;
import com.api.bookstore.library_backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @RequestParam String name,
            @RequestParam(required = false) String description) {
        RoleResponse roleResponse = roleService.createRole(name, description);
        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .message("Role created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(roleResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        ApiResponse<List<RoleResponse>> response = ApiResponse.<List<RoleResponse>>builder()
                .message("Roles retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(roles)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        RoleResponse role = roleService.getRoleById(id);
        ApiResponse<RoleResponse> response = ApiResponse.<RoleResponse>builder()
                .message("Role retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(role)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}