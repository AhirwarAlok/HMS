package com.landminesoft.HMS.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RevenueResponse {

    private BigDecimal total;
    private long appointments;
}