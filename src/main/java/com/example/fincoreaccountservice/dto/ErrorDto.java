package com.example.fincoreaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для передачи информации об ошибке
 */
@Data
@AllArgsConstructor
public class ErrorDto {
    private String message;
    private String code;

    public static final ErrorDto NOT_FOUND = new ErrorDto("Bank book not found", "NOT_FOUND");
    public static final ErrorDto INVALID_REQUEST = new ErrorDto("Invalid request", "INVALID_REQUEST");
    public static final ErrorDto DUPLICATE_ACCOUNT = new ErrorDto("Account with this number and currency already exists", "DUPLICATE_ACCOUNT");
    public static final ErrorDto NUMBER_CHANGE = new ErrorDto("Account number cannot be changed", "NUMBER_CHANGE");
    public static final ErrorDto MISSING_USER_ID = new ErrorDto("User ID is required", "MISSING_USER_ID");
    public static final ErrorDto MISSING_BANKBOOK_ID = new ErrorDto("Bank book ID is required", "MISSING_BANKBOOK_ID");
}
