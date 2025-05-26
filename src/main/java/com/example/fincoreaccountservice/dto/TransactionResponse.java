package com.example.fincoreaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private boolean success;
    private String message;
    private LocalDateTime timestamp;
    private BigDecimal amount;
    private Integer sourceId;
    private Integer targetId;
}
