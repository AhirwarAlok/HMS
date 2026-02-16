package com.landminesoft.HMS.repository;

import com.landminesoft.HMS.entity.Appointment;
import com.landminesoft.HMS.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_Id(Long patientId);

    List<Appointment> findByDoctor_Id(Long doctorId);

    Boolean existsByDoctor_IdAndAppointmentDateAndAppointmentTime(
            Long doctorId,
            LocalDate date,
            LocalTime time,
            AppointmentStatus status
    );

    @Query("""
       SELECT COUNT(a)
       FROM Appointment a
       WHERE a.status = com.landminesoft.HMS.entity.AppointmentStatus.COMPLETED
       AND a.appointmentDate BETWEEN :start AND :end
       AND (:doctorId IS NULL OR a.doctor.id = :doctorId)
       """)
    long countCompletedAppointmentsByDoctorAndDate(
            @Param("doctorId") Long doctorId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );




}
