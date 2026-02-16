package com.landminesoft.HMS.repository;

import com.landminesoft.HMS.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByAppointment_Id(Long appointmentId);

    Optional<Payment> findByAppointment_Id(Long appointmentId);

    @Query("""
       SELECT COALESCE(SUM(p.amount), 0)
       FROM Payment p
       WHERE p.paymentStatus = com.landminesoft.HMS.entity.PaymentStatus.PAID
       """)
    BigDecimal getTotalRevenue();

    @Query("""
       SELECT COALESCE(SUM(p.amount), 0)
       FROM Payment p
       WHERE p.paymentStatus = com.landminesoft.HMS.entity.PaymentStatus.PAID
       AND p.paidAt BETWEEN :start AND :end
       """)
    BigDecimal getMonthlyRevenue(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
       SELECT COALESCE(SUM(p.amount), 0)
       FROM Payment p
       WHERE p.paymentStatus = com.landminesoft.HMS.entity.PaymentStatus.PAID
       AND p.paidAt BETWEEN :start AND :end
       AND (:doctorId IS NULL OR p.doctor.id = :doctorId)
       """)
    BigDecimal getRevenueByDoctorAndDate(
            @Param("doctorId") Long doctorId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );





}
