package com.example.fincoreaccountservice.dto;

import com.example.fincoreaccountservice.validation.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO для представления банковского счета
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankBookDto {
    /**
     * ID счета
     */
    @Null(message = "ID должен быть null при создании")
    private Integer id;

    /**
     * ID пользователя-владельца счета
     */
    @NotNull(message = "User ID не может быть null")
    private Integer userId;

    /**
     * Номер счета
     */
    @NotBlank(message = "Номер счета не может быть пустым")
    private String number;

    /**
     * Текущий баланс счета
     */
    @NotNull(message = "Сумма не может быть null")
    @Positive(message = "Сумма не может быть отрицательной")
    private BigDecimal amount;

    /**
     * Валюта счета
     */
    @NotNull(message = "Валюта не может быть null")
    @Currency
    private String currency;
}
