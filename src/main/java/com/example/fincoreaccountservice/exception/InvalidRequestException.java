package com.example.fincoreaccountservice.exception;

/**
 * Исключение при невалидных параметрах запроса
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
