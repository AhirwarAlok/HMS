package com.landminesoft.HMS.dto;

import com.landminesoft.HMS.entity.Patient;
import lombok.Data;

@Data
public class MedicalRecordRequest {
    private Long appointmentId;

    private String diagnosis;

    private String prescription;



    private String dosageInstructions;

    private String notes;

    private String attachmentPath;
}
