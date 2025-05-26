package com.example.fincoreaccountservice.exception;

/**
 * Исключение при отсутствии счета
 */
public class BankBookNotFoundException extends RuntimeException {
    public BankBookNotFoundException(String message) {
        super(message);
    }
}
