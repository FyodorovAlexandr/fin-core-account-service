package com.example.fincoreaccountservice.exception;

/**
 * Исключение при попытке создать дубликат счета
 */
public class DuplicateBankBookException extends RuntimeException {
    public DuplicateBankBookException(String message) {
        super(message);
    }
}
