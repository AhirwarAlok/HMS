package com.landminesoft.HMS.dto;

import com.landminesoft.HMS.entity.PaymentMethod;
import com.landminesoft.HMS.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long appointmentId;
    private String patientName;
    private String doctorName;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private LocalDateTime paidAt;
}
