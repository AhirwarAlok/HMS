package com.landminesoft.HMS.repository;

import com.landminesoft.HMS.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    boolean existsByAppointment_Id(Long appointmentId);
    List<MedicalRecord> findByPatient_Id(Long patientId);
}
