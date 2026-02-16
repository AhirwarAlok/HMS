package com.landminesoft.HMS.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    @NotNull(message = "Doctor ID is required")
    @Positive(message = "Doctor ID is invalid, only +ve IDs allowed.")
    private Long doctorId;

    @NotNull(message = "Patient ID is required" )
    @Positive(message = "Patient ID is invalid, only +ve IDs allowed.")
    private Long patientId;

    @NotNull(message = "Appointment Date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "Time is required")
    private LocalTime appointmentTime;

    @Size(max = 500, message = "Symptoms cannot exceed 500 characters")
    private String symptoms;
}
