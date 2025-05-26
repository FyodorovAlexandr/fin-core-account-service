package com.example.fincoreaccountservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {
    @NotNull
    private Integer sourceAccountId;

    @NotNull
    private Integer targetAccountId;

    @NotNull
    private Integer sourceUserId;

    @NotNull
    private Integer targetUserId;

    @NotNull @Positive
    private BigDecimal amount;

    private String currency;
}
