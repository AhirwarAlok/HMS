package com.landminesoft.HMS.config;

import com.landminesoft.HMS.entity.Role;
import com.landminesoft.HMS.entity.User;
import com.landminesoft.HMS.repository.RoleRepository;
import com.landminesoft.HMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Create roles if not exist
        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseGet(() -> roleRepository.save(
                        createRole("ADMIN")));

        Role doctorRole = roleRepository.findByRoleName("DOCTOR")
                .orElseGet(() -> roleRepository.save(
                        createRole("DOCTOR")));

        Role patientRole = roleRepository.findByRoleName("PATIENT")
                .orElseGet(() -> roleRepository.save(
                        createRole("PATIENT")));

        // Create Admin user
        if (userRepository.findByEmail("admin@hms.com").isEmpty()) {

            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@hms.com");
            admin.setPassword(
                    passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
        }
    }

    private Role createRole(String name) {
        Role role = new Role();
        role.setRoleName(name);
        return role;
    }
}
