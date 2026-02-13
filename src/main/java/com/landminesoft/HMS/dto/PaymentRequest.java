package com.landminesoft.HMS.dto;

import com.landminesoft.HMS.entity.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long appointmentId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
