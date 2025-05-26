package com.example.fincoreaccountservice.exception;

/**
 * Исключение при отсутствии валюты
 */
public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
