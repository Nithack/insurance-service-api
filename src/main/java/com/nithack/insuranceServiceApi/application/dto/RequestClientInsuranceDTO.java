package com.nithack.insuranceServiceApi.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RequestClientInsuranceDTO {
    @NotNull(message = "O ID do seguro é obrigatório")
    private UUID insuranceId;

    @NotNull(message = "O ID do cliente é obrigatório")
    private UUID clientId;

    @NotNull(message = "A duração em meses é obrigatória")
    @Positive(message = "A duração em meses deve ser positiva")
    private Integer durationMonths;
}
