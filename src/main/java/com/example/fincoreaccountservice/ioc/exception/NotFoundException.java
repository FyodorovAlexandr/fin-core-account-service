package com.example.fincoreaccountservice.ioc.exception;

/**
 * Исключение, выбрасываемое когда данные не найдены
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
