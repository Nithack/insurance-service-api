package com.nithack.insuranceServiceApi.application.exception;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(UUID clientId) {
        super("Client not found with id: " + clientId);
    }
}
