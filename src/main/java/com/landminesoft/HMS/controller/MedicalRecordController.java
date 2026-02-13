package com.landminesoft.HMS.controller;

import com.landminesoft.HMS.dto.MedicalRecordRequest;
import com.landminesoft.HMS.dto.MedicalRecordResponse;
import com.landminesoft.HMS.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hms/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(
            @RequestBody MedicalRecordRequest request) {

        MedicalRecordResponse response = medicalRecordService.createMedicalRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> getRecordsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                medicalRecordService.getMedicalRecordsByPatient(patientId)
        );
    }
}
