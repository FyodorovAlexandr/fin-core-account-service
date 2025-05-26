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
}
