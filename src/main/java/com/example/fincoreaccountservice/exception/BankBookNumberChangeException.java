package com.example.fincoreaccountservice.exception;

/**
 * Исключение при попытке изменить номер счета
 */
public class BankBookNumberChangeException extends RuntimeException {
    public BankBookNumberChangeException(String message) {
        super(message);
    }
}
