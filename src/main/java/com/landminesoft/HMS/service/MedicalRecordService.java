package com.landminesoft.HMS.service;

import com.landminesoft.HMS.dto.MedicalRecordRequest;
import com.landminesoft.HMS.dto.MedicalRecordResponse;
import com.landminesoft.HMS.entity.Appointment;
import com.landminesoft.HMS.entity.AppointmentStatus;
import com.landminesoft.HMS.entity.MedicalRecord;
import com.landminesoft.HMS.exception.ConflictException;
import com.landminesoft.HMS.exception.ResourceNotFoundException;
import com.landminesoft.HMS.repository.AppointmentRepository;
import com.landminesoft.HMS.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new ConflictException(
                    "Medical record can only be created for scheduled appointments");
        }
        boolean exists = medicalRecordRepository
                .existsByAppointment_Id(request.getAppointmentId());
        if (exists) {
            throw new ConflictException("Medical record already exists for this appointment");
        }

        MedicalRecord record = new MedicalRecord();
        record.setAppointment(appointment);
        record.setDoctor(appointment.getDoctor());
        record.setPatient(appointment.getPatient());
        record.setDiagnosis(request.getDiagnosis());
        record.setPrescription(request.getPrescription());
        record.setDosageInstructions(request.getDosageInstructions());
        record.setNotes(request.getNotes());
        record.setAttachmentPath(request.getAttachmentPath());
        record.setRecordDate(LocalDateTime.now());

        MedicalRecord savedRecord = medicalRecordRepository.save(record);
        // Update appointment status → COMPLETED
        appointment.setStatus(AppointmentStatus.COMPLETED);

        return mapToResponse(savedRecord);

    }
    public List<MedicalRecordResponse> getMedicalRecordsByPatient(Long patientId) {

        return medicalRecordRepository
                .findByPatient_Id(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private MedicalRecordResponse mapToResponse(MedicalRecord record) {
        return MedicalRecordResponse.builder()
                .id(record.getId())
                .appointmentId(record.getAppointment().getId())
                .doctorId(record.getDoctor().getId())
                .patientId(record.getPatient().getId())
                .doctorName(record.getDoctor().getUser().getName())
                .patientName(record.getPatient().getUser().getName())
                .diagnosis(record.getDiagnosis())
                .prescription(record.getPrescription())
                .dosageInstructions(record.getDosageInstructions())
                .notes(record.getNotes())
                .attachmentPath(record.getAttachmentPath())
                .recordDate(record.getRecordDate())
                .build();
    }
}
