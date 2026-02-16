package com.landminesoft.HMS.service;

import com.landminesoft.HMS.dto.DashboardResponse;
import com.landminesoft.HMS.dto.RevenueResponse;
import com.landminesoft.HMS.exception.ResourceNotFoundException;
import com.landminesoft.HMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;

    public DashboardResponse getDashboardData() {

        long totalDoctors = doctorRepository.count();
        long totalPatients = patientRepository.count();
        long totalAppointments = appointmentRepository.count();
        BigDecimal totalRevenue = paymentRepository.getTotalRevenue();

        YearMonth currentMonth = YearMonth.now();

        LocalDateTime startOfMonth = currentMonth
                .atDay(1)
                .atStartOfDay();

        LocalDateTime endOfMonth = currentMonth
                .atEndOfMonth()
                .atTime(23, 59, 59);

        BigDecimal monthlyRevenue = paymentRepository
                .getMonthlyRevenue(startOfMonth, endOfMonth);


        return DashboardResponse.builder()
                .totalDoctors(totalDoctors)
                .totalPatients(totalPatients)
                .totalAppointments(totalAppointments)
                .totalRevenue(totalRevenue)
                .monthlyRevenue(monthlyRevenue)
                .build();
    }

    public RevenueResponse getRevenue(
            Long doctorId,
            LocalDate from,
            LocalDate to) {

        if (doctorId != null && !doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }

        LocalDateTime startDateTime = from.atStartOfDay();
        LocalDateTime endDateTime = to.atTime(23, 59, 59);

        BigDecimal totalRevenue = paymentRepository
                .getRevenueByDoctorAndDate(doctorId, startDateTime, endDateTime);

        long appointmentCount = appointmentRepository
                .countCompletedAppointmentsByDoctorAndDate(doctorId, from, to);

        return RevenueResponse.builder()
                .total(totalRevenue)
                .appointments(appointmentCount)
                .build();
    }

}
