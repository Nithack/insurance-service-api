package com.nithack.insuranceServiceApi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInsuranceDTO {
    private UUID id;
    private UUID clientId;
    private UUID insuranceId;
    private String name;
    private double totalCost;
    private double monthlyCost;
    private Integer durationMonths;
    private List<String> benefits;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}