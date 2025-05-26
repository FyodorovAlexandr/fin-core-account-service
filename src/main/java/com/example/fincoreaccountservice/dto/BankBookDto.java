package com.example.fincoreaccountservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для представления банковского счета
 */
@Data
@AllArgsConstructor
public class BankBookDto {
    private Integer id;
    private Integer userId;
    private String number;
    private BigDecimal amount;
    private String currency;
}
