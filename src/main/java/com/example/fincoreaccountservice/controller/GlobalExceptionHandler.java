package com.example.fincoreaccountservice.controller;

import com.example.fincoreaccountservice.dto.ErrorDto;
import com.example.fincoreaccountservice.exception.BankBookNotFoundException;
import com.example.fincoreaccountservice.exception.BankBookNumberChangeException;
import com.example.fincoreaccountservice.exception.DuplicateBankBookException;
import com.example.fincoreaccountservice.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик исключений приложения
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка исключения "Счет не найден"
     */
    @ExceptionHandler(BankBookNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBankBookNotFoundException(BankBookNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.NOT_FOUND);
    }

    /**
     * Обработка исключения "Попытка изменить номер счета"
     */
    @ExceptionHandler(BankBookNumberChangeException.class)
    public ResponseEntity<ErrorDto> handleBankBookNumberChangeException(BankBookNumberChangeException exception) {
        return ResponseEntity.badRequest().body(ErrorDto.NUMBER_CHANGE);
    }

    /**
     * Обработка исключения "Дубликат счета"
     */
    @ExceptionHandler(DuplicateBankBookException.class)
    public ResponseEntity<ErrorDto> handleDuplicateBankBookException(DuplicateBankBookException exception) {
        return ResponseEntity.badRequest().body(ErrorDto.DUPLICATE_ACCOUNT);
    }

    /**
     * Обработка ошибок валидации
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidRequestException.class})
    public ResponseEntity<ErrorDto> handleValidationExceptions(Exception exception) {
        String errorMessage = exception instanceof MethodArgumentNotValidException
                ? ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors().getFirst().getDefaultMessage()
                : exception.getMessage();

        return ResponseEntity.badRequest().body(new ErrorDto(errorMessage, ErrorDto.INVALID_REQUEST.getCode()));
    }
}
