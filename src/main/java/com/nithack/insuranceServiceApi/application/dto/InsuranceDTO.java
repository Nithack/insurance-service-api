package com.nithack.insuranceServiceApi.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) para operações de Insurance, contendo validações.
 */
@Data
@Builder
public class InsuranceDTO {

    private UUID id;

    @NotBlank(message = "O nome do plano é obrigatório")
    private String name;

    @NotNull(message = "O valor da cobertura é obrigatório")
    @Positive(message = "O valor da cobertura deve ser positivo")
    private Double coverageAmount;

    @NotNull(message = "O custo mensal é obrigatório")
    @Positive(message = "O custo mensal deve ser positivo")
    private Double monthlyCost;

    @NotEmpty(message = "Os benefícios devem ser especificados")
    private List<@NotBlank(message = "Benefício não pode ser vazio") String> benefits;
}