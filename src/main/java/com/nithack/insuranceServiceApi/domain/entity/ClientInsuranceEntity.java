package com.nithack.insuranceServiceApi.domain.entity;

import com.nithack.insuranceServiceApi.domain.enums.InsuranceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientInsuranceEntity {
    private UUID id;
    private UUID clientId;
    private String cpf;
    private InsuranceEntity insurance;
    private double monthlyCost;
    private Integer durationMonths;
    private LocalDate startDate;
    private LocalDate endDate;
    private InsuranceStatus status;

    public double getTotalCost() {
        return monthlyCost * durationMonths;
    }
}
