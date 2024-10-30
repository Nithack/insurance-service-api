package com.nithack.insuranceServiceApi.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
public class ClientDTO {
    private UUID id;
    private String cpf;
}
