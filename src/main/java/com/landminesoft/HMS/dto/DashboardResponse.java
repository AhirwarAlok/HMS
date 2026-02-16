package com.landminesoft.HMS.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardResponse {
    private long totalDoctors;
    private long totalPatients;
    private long totalAppointments;
    private BigDecimal totalRevenue;
    private BigDecimal monthlyRevenue;
}
