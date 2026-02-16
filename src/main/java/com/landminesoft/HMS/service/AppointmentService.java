package com.landminesoft.HMS.service;

import com.landminesoft.HMS.dto.AppointmentResponse;
import com.landminesoft.HMS.entity.Appointment;
import com.landminesoft.HMS.entity.AppointmentStatus;
import com.landminesoft.HMS.exception.BadRequestException;
import com.landminesoft.HMS.exception.ConflictException;
import com.landminesoft.HMS.exception.ResourceNotFoundException;
import com.landminesoft.HMS.repository.AppointmentRepository;
import com.landminesoft.HMS.repository.DoctorRepository;
import com.landminesoft.HMS.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public AppointmentResponse bookAppointment(Long doctorId,
                                               Long patientId,
                                               LocalDate date,
                                               LocalTime time,
                                               String symptoms
    ){
        boolean isSlotBooked = appointmentRepository
                .existsByDoctor_IdAndAppointmentDateAndAppointmentTime(
                        doctorId,
                        date,
                        time,
                        AppointmentStatus.SCHEDULED);

        if(isSlotBooked){
            throw new ConflictException("Appointment already exists for this Doctor and Slot");
        }
        Appointment appointment = new Appointment();
        appointment.setDoctor(
                doctorRepository.findById(doctorId)
                        .orElseThrow(()-> new ResourceNotFoundException("Doctor not found"))
        );
        appointment.setPatient(
                patientRepository.findById(patientId)
                        .orElseThrow( ()-> new ResourceNotFoundException("Patient not found"))
        );
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setSymptoms(symptoms);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId){
        return appointmentRepository.findByPatient_Id(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor_Id(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public AppointmentResponse cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        LocalDateTime appointmentDateTime = LocalDateTime.of(
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime()
        );
        if (appointmentDateTime.isBefore(LocalDateTime.now().plusHours(24))) {
            throw new BadRequestException("Cannot cancel within 24 hours of appointment time");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return mapToResponse(appointment);
    }

    public Long getAppointmentCount() {
        return  appointmentRepository.count();
    }



    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .doctorName(appointment.getDoctor().getUser().getName())
                .patientName(appointment.getPatient().getUser().getName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus().name())
                .symptoms(appointment.getSymptoms())
                .createdAt(appointment.getCreatedAt())
                .build();
    }


}
