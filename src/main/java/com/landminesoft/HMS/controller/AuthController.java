package com.landminesoft.HMS.controller;

import com.landminesoft.HMS.dto.RegisterDoctorRequest;
import com.landminesoft.HMS.dto.RegisterPatientRequest;
import com.landminesoft.HMS.dto.auth.*;
import com.landminesoft.HMS.security.JwtService;
import com.landminesoft.HMS.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hms/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(AuthResponse.builder()
                        .token(token)
                        .build()
        );
    }

    @PostMapping("/register/patient")
    public ResponseEntity<String> registerPatient(
            @Valid @RequestBody RegisterPatientRequest request) {

        authService.registerPatient(request);
        return ResponseEntity.ok("Patient registered successfully");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/doctor")
    public ResponseEntity<String> registerDoctor(
            @Valid @RequestBody RegisterDoctorRequest request) {

        authService.registerDoctor(request);
        return ResponseEntity.ok("Doctor registered successfully");
    }

}
