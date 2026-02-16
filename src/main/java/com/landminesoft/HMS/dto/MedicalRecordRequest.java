package com.landminesoft.HMS.dto;

import com.landminesoft.HMS.entity.Patient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicalRecordRequest {
    @NotNull(message = "Appointment ID is required")
    @Positive(message = "Appointment ID must be positive")
    private Long appointmentId;

    @NotBlank(message = "Diagnosis is required")
    @Size(max = 1000)
    private String diagnosis;

    @NotBlank(message = "Prescription is required")
    @Size(max = 1000)
    private String prescription;



    private String dosageInstructions;

    private String notes;

    private String attachmentPath;
}
