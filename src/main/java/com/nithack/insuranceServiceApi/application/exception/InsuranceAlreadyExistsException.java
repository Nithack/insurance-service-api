package com.nithack.insuranceServiceApi.application.exception;


public class InsuranceAlreadyExistsException extends RuntimeException {
    public InsuranceAlreadyExistsException(String cpf) {
        super("Client already exists with CPF: " + cpf);
    }
}
