package com.nithack.insuranceServiceApi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe para padronizar a resposta de erro com mensagem e status HTTP.
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDTO {
    private Integer status;
    private String message;
}
