package com.example.fincoreaccountservice.exception;

public class DuplicateBankBookException extends RuntimeException {
    public DuplicateBankBookException(String message) {
        super(message);
    }
}
