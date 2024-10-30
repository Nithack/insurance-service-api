package com.nithack.insuranceServiceApi.application.exception;


import java.util.UUID;

public class ClientInsuranceAlreadyExistsException extends RuntimeException {
    public ClientInsuranceAlreadyExistsException(UUID cpf) {
        super("Client insurance already exists with clientId: " + cpf.toString());
    }
}
