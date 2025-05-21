package com.example.fincoreaccountservice.ioc.exception;

/**
 * Исключение, выбрасываемое при ошибках обработки данных
 */
public class ProcessException extends RuntimeException {
    public ProcessException(String message) {
        super(message);
    }
}
