package com.api.bookstore.library_backend.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.api.bookstore.library_backend.model.Permission;
import com.api.bookstore.library_backend.model.Role;
import com.api.bookstore.library_backend.model.User;
import com.api.bookstore.library_backend.repository.PermissionRepository;
import com.api.bookstore.library_backend.repository.RoleRepository;
import com.api.bookstore.library_backend.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");

        // 1. Tạo Permissions
        initializePermissions();

        // 2. Tạo Roles
        initializeRoles();

        // 3. Tạo Admin User
        initializeAdminUser();

        log.info("Data initialization completed!");
    }

    private void initializePermissions() {
        log.info("Initializing permissions...");

        String[] permissionNames = {
                "READ_BOOK",
                "CREATE_BOOK",
                "UPDATE_BOOK",
                "DELETE_BOOK",
                "READ_CATEGORY",
                "CREATE_CATEGORY",
                "UPDATE_CATEGORY",
                "DELETE_CATEGORY",
                "READ_ORDER",
                "CREATE_ORDER",
                "UPDATE_ORDER",
                "DELETE_ORDER",
                "READ_USER",
                "CREATE_USER",
                "UPDATE_USER",
                "DELETE_USER",
                "READ_ROLE",
                "CREATE_ROLE",
                "UPDATE_ROLE",
                "DELETE_ROLE",
                "READ_PERMISSION",
                "CREATE_PERMISSION",
                "UPDATE_PERMISSION",
                "DELETE_PERMISSION"
        };

        for (String permissionName : permissionNames) {
            if (!permissionRepository.existsByName(permissionName)) {
                Permission permission = Permission.builder()
                        .name(permissionName)
                        .description("Permission to " + permissionName.toLowerCase().replace("_", " "))
                        .isDeleted(false)
                        .build();
                permissionRepository.save(permission);
                log.debug("Created permission: {}", permissionName);
            }
        }

        log.info("Permissions initialized successfully!");
    }

    private void initializeRoles() {
        log.info("Initializing roles...");

        // Tạo ADMIN role nếu chưa có
        if (!roleRepository.existsByName("ADMIN")) {
            Role adminRole = Role.builder()
                    .name("ADMIN")
                    .description("Administrator role with full access")
                    .isDeleted(false)
                    .build();

            // Gán tất cả permissions cho ADMIN
            Set<Permission> allPermissions = new HashSet<>(permissionRepository.findAll());
            adminRole.setPermissions(allPermissions);

            roleRepository.save(adminRole);
            log.info("Created ADMIN role with all permissions");
        }

        // Tạo CUSTOMER role nếu chưa có
        if (!roleRepository.existsByName("CUSTOMER")) {
            Role customerRole = Role.builder()
                    .name("CUSTOMER")
                    .description("Customer role with limited access")
                    .isDeleted(false)
                    .build();

            // Gán permissions cho CUSTOMER
            Set<Permission> customerPermissions = new HashSet<>();
            customerPermissions.add(permissionRepository.findByName("READ_BOOK").orElse(null));
            customerPermissions.add(permissionRepository.findByName("READ_CATEGORY").orElse(null));
            customerPermissions.add(permissionRepository.findByName("CREATE_ORDER").orElse(null));
            customerPermissions.add(permissionRepository.findByName("READ_ORDER").orElse(null));

            customerPermissions.removeIf(p -> p == null);
            customerRole.setPermissions(customerPermissions);

            roleRepository.save(customerRole);
            log.info("Created CUSTOMER role");
        }

        // Tạo MANAGER role nếu chưa có
        if (!roleRepository.existsByName("MANAGER")) {
            Role managerRole = Role.builder()
                    .name("MANAGER")
                    .description("Manager role with moderate access")
                    .isDeleted(false)
                    .build();

            // Gán permissions cho MANAGER
            Set<Permission> managerPermissions = new HashSet<>();
            managerPermissions.add(permissionRepository.findByName("READ_BOOK").orElse(null));
            managerPermissions.add(permissionRepository.findByName("CREATE_BOOK").orElse(null));
            managerPermissions.add(permissionRepository.findByName("UPDATE_BOOK").orElse(null));
            managerPermissions.add(permissionRepository.findByName("READ_CATEGORY").orElse(null));
            managerPermissions.add(permissionRepository.findByName("CREATE_CATEGORY").orElse(null));
            managerPermissions.add(permissionRepository.findByName("READ_ORDER").orElse(null));
            managerPermissions.add(permissionRepository.findByName("UPDATE_ORDER").orElse(null));

            managerPermissions.removeIf(p -> p == null);
            managerRole.setPermissions(managerPermissions);

            roleRepository.save(managerRole);
            log.info("Created MANAGER role");
        }

        log.info("Roles initialized successfully!");
    }

    private void initializeAdminUser() {
        log.info("Initializing admin user...");

        if (!userRepository.existsByUsername("admin")) {
            User adminUser = User.builder()
                    .username("admin")
                    .email("admin@bookstore.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("User")
                    .isDeleted(false)
                    .build();

            // Gán ADMIN role
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
            log.info("Created default admin user - Username: admin, Password: admin123");
        } else {
            log.info("Admin user already exists!");
        }
    }
}