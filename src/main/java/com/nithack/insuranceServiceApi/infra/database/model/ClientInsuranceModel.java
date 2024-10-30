package com.nithack.insuranceServiceApi.infra.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client_insurances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientInsuranceModel {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", nullable = false)
    private InsuranceModel insurance;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double monthlyCost;

    @Column(nullable = false)
    private Integer durationMonths;
}
