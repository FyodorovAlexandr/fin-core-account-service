package com.example.fincoreaccountservice.controller;

import com.example.fincoreaccountservice.dto.ErrorDto;
import com.example.fincoreaccountservice.exception.*;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик исключений приложения
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    /**
     * Обработка исключения "Счет не найден"
     */
    @ExceptionHandler(BankBookNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBankBookNotFoundException(BankBookNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(exception.getMessage(),
                "BANK_BOOK_NOT_FOUND"));
    }

    /**
     * Обработка исключения "Пользователь не найден"
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(exception.getMessage(), "USER_NOT_FOUND"));
    }

    /**
     * Обработка ошибок валидации
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleValidationExceptions(Exception exception) {
        String errorMessage = exception instanceof MethodArgumentNotValidException
                ? ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors().getFirst().getDefaultMessage()
                : ((ConstraintViolationException) exception).getConstraintViolations().iterator().next().getMessage();

        return ResponseEntity.badRequest().body(new ErrorDto(errorMessage, "VALIDATION_ERROR"));
    }

    /**
     * Обработка исключения "Дубликат счета"
     */
    @ExceptionHandler(DuplicateBankBookException.class)
    public ResponseEntity<ErrorDto> handleDuplicateBankBookException(DuplicateBankBookException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage(), "DUPLICATE_BANK_BOOK"));
    }

    /**
     * Обработка исключения "Попытка изменить номер счета"
     */
    @ExceptionHandler(BankBookNumberChangeException.class)
    public ResponseEntity<ErrorDto> handleBankBookNumberChangeException(BankBookNumberChangeException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage(), "NUMBER_CHANGE"));
    }

    /**
     * Обработка исключения "Валюта не найдена"
     */
    @ExceptionHandler(CurrencyException.class)
    public ResponseEntity<ErrorDto> handleCurrencyNotFoundException(CurrencyException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(exception.getMessage(),
                "CURRENCY_NOT_FOUND"));
    }

    /**
     * Обработка исключения "Несоответствие валют"
     */
    @ExceptionHandler(CurrencyNotEqualsException.class)
    public ResponseEntity<ErrorDto> handleCurrencyNotEqualsException(CurrencyNotEqualsException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage(), "CURRENCY_NOT_EQUALS"));
    }

    /**
     * Обработка исключения "Дубликат email"
     */
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorDto> handleDuplicateEmailException(DuplicateEmailException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage(), "DUPLICATE_EMAIL"));
    }
}
