package com.example.fincoreaccountservice.exception;

public class BankBookNotFoundException extends RuntimeException {
    public BankBookNotFoundException(String message) {
        super(message);
    }
}
