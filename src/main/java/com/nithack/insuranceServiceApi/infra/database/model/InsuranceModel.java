package com.nithack.insuranceServiceApi.infra.database.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "insurances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceModel {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double coverageAmount;

    @Column(nullable = false)
    private double monthlyCost;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "insurance_benefits", joinColumns = @JoinColumn(name = "insurance_id"))
    @Column(name = "benefit")
    private List<String> benefits;
}