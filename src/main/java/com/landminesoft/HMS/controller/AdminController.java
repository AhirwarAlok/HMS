package com.landminesoft.HMS.controller;

import com.landminesoft.HMS.dto.DashboardResponse;
import com.landminesoft.HMS.dto.RevenueResponse;
import com.landminesoft.HMS.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/hms/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(adminService.getDashboardData());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> getRevenue(
            @RequestParam(required = false) Long doctorId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return ResponseEntity.ok(adminService.getRevenue(doctorId, from, to));
    }

}
