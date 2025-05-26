package com.example.fincoreaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи информации об ошибке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String message;
    private String code;
}
