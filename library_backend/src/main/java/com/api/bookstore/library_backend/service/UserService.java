package com.api.bookstore.library_backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.api.bookstore.library_backend.dto.request.UserCreationRequest;
import com.api.bookstore.library_backend.dto.request.UserUpdateRequest;
import com.api.bookstore.library_backend.dto.response.UserResponse;
import com.api.bookstore.library_backend.mapper.UserMapper;
import com.api.bookstore.library_backend.model.Role;
import com.api.bookstore.library_backend.model.User;
import com.api.bookstore.library_backend.repository.RoleRepository;
import com.api.bookstore.library_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsDeleted(false);

        if (request.getRoleIds() == null || request.getRoleIds().isEmpty()) {
            Role customerRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
            Set<Role> roles = new HashSet<>();
            roles.add(customerRole);
            user.setRoles(roles);
        } else {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : request.getRoleIds()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        User savedUser = userRepository.save(user);
        log.info("User created: {}", savedUser.getUsername());
        return userMapper.toUserResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        if (user.getIsDeleted()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getIsDeleted())
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (user.getIsDeleted()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userMapper.updateUser(user, request);

        User updatedUser = userRepository.save(user);
        log.info("User updated: {}", updatedUser.getUsername());
        return userMapper.toUserResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setIsDeleted(true);
        userRepository.save(user);
        log.info("User deleted: {}", user.getUsername());
    }
}