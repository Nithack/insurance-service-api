package com.nithack.insuranceServiceApi.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceEntity {
    private UUID id;
    private String name;
    private double coverageAmount;
    private double monthlyCost;
    private List<String> benefits;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
