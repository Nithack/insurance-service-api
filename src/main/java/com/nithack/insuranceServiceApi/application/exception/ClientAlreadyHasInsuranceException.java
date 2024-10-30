package com.nithack.insuranceServiceApi.application.exception;

public class ClientAlreadyHasInsuranceException extends RuntimeException {
    public ClientAlreadyHasInsuranceException(String clientId) {
        super("Client already has insurance, clientId: " + clientId);
    }
}
