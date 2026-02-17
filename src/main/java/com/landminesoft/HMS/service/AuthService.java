package com.landminesoft.HMS.service;

import com.landminesoft.HMS.dto.RegisterDoctorRequest;
import com.landminesoft.HMS.dto.RegisterPatientRequest;
import com.landminesoft.HMS.entity.Doctor;
import com.landminesoft.HMS.entity.Patient;
import com.landminesoft.HMS.entity.Role;
import com.landminesoft.HMS.entity.User;
import com.landminesoft.HMS.exception.ConflictException;
import com.landminesoft.HMS.exception.ResourceNotFoundException;
import com.landminesoft.HMS.repository.DoctorRepository;
import com.landminesoft.HMS.repository.PatientRepository;
import com.landminesoft.HMS.repository.RoleRepository;
import com.landminesoft.HMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerPatient(RegisterPatientRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        Role role = roleRepository.findByRoleName("PATIENT")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(role);

        userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setEmergencyContact(request.getEmergencyContact());

        patientRepository.save(patient);
    }

    public void registerDoctor(RegisterDoctorRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        Role role = roleRepository.findByRoleName("DOCTOR")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(role);

        userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(request.getSpecialization());

        doctorRepository.save(doctor);
    }
}
