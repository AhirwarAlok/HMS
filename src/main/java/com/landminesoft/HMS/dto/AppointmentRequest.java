package com.landminesoft.HMS.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    @NotNull(message = "Patient ID is required" )
    private Long doctorId;
    @NotNull(message = "Doctor ID is required")
    private Long patientId;
    @NotNull(message = "Date is required")
    private LocalDate appointmentDate;
    @NotNull(message = "Time is required")
    private LocalTime appointmentTime;
    private String symptoms;
}
