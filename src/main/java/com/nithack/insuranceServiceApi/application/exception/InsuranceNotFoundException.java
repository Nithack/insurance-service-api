package com.nithack.insuranceServiceApi.application.exception;

import java.util.UUID;

public class InsuranceNotFoundException extends RuntimeException {
    public InsuranceNotFoundException(UUID insuranceId) {
        super("Insurance not found with id: " + insuranceId);
    }
}
