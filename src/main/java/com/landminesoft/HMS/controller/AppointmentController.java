package com.landminesoft.HMS.controller;

import com.landminesoft.HMS.dto.AppointmentRequest;
import com.landminesoft.HMS.dto.AppointmentResponse;
import com.landminesoft.HMS.entity.Appointment;
import com.landminesoft.HMS.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hms/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(@Valid @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.bookAppointment(request.getDoctorId(), request.getPatientId(), request.getAppointmentDate(), request.getAppointmentTime(), request.getSymptoms());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponse>> getPatientAppointments(@Valid @PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments(@Valid @PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }


}
