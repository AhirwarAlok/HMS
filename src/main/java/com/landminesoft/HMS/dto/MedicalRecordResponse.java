package com.landminesoft.HMS.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MedicalRecordResponse {
    private Long id;
    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private String doctorName;
    private String patientName;
    private String diagnosis;
    private String prescription;
    private String dosageInstructions;
    private String notes;
    private String attachmentPath;
    private LocalDateTime recordDate;
}
