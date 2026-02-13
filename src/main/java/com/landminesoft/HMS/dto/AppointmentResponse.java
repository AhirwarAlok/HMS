package com.landminesoft.HMS.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class AppointmentResponse {

    private Long id;

    private Long doctorId;
    private Long patientId;

    private String doctorName;
    private String patientName;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String status;
    private String symptoms;

    private LocalDateTime createdAt;
}
