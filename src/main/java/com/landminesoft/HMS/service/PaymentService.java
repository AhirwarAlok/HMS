package com.landminesoft.HMS.service;

import com.landminesoft.HMS.dto.PaymentRequest;
import com.landminesoft.HMS.dto.PaymentResponse;
import com.landminesoft.HMS.entity.Appointment;
import com.landminesoft.HMS.entity.AppointmentStatus;
import com.landminesoft.HMS.entity.Payment;
import com.landminesoft.HMS.entity.PaymentStatus;
import com.landminesoft.HMS.repository.AppointmentRepository;
import com.landminesoft.HMS.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public PaymentResponse makePayment(PaymentRequest request) {

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Payment allowed only after appointment completion");
        }

        Optional<Payment> existing = paymentRepository.findByAppointment_Id(request.getAppointmentId());
        if (existing.isPresent()) {
            throw new RuntimeException("Payment already exists for this appointment");
        }

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setDoctor(appointment.getDoctor());
        payment.setPatient(appointment.getPatient());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId(UUID.randomUUID().toString());

        Payment saved = paymentRepository.save(payment);

        return mapToResponse(saved);
    }
    @Transactional
    public PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus(status);

        if (status == PaymentStatus.PAID) {
            payment.setPaidAt(LocalDateTime.now());
            sendPaymentConfirmationEmail(payment);
        }

        return mapToResponse(payment);
    }
    private void sendPaymentConfirmationEmail(Payment payment) {
        System.out.println("Sending payment confirmation email to: "
                + payment.getPatient().getUser().getEmail());
    }


    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .id(payment.getId())
                .appointmentId(payment.getAppointment().getId())
                .doctorName(payment.getDoctor().getUser().getName())
                .patientName(payment.getPatient().getUser().getName())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .build();
    }
}

