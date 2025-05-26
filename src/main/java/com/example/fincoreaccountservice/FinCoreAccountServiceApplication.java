package com.example.fincoreaccountservice;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.service.BankBookService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

/**
 * Главный класс приложения для управления банковскими счетами
 */
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class FinCoreAccountServiceApplication {
    private final BankBookService bankBookService;

    public static void main(String[] args) {
        SpringApplication.run(FinCoreAccountServiceApplication.class, args);
    }

    /**
     * Инициализация тестовых данных при старте приложения
     */
    @PostConstruct
    public void initTestData() {
        try {
            // Счета для пользователя 1
            bankBookService.createBankBook(new BankBookDto(1, 1, "40817810000000000001",
                    BigDecimal.valueOf(1000.50), "RUB"));
            bankBookService.createBankBook(new BankBookDto(2, 1, "40817810000000000001",
                    BigDecimal.valueOf(500.75), "USD"));

            // Счета для пользователя 2
            bankBookService.createBankBook(new BankBookDto(3, 2, "40817810000000000002",
                    BigDecimal.valueOf(2500.00), "RUB"));
            bankBookService.createBankBook(new BankBookDto(4, 2, "40817810000000000002",
                    BigDecimal.valueOf(1200.30), "EUR"));

            // Счета для пользователя 3
            bankBookService.createBankBook(new BankBookDto(5, 3, "40817810000000000003",
                    BigDecimal.valueOf(3500.00), "RUB"));
        } catch (Exception exception) {
            String message = String.format("Ошибка при инициализации тестовых данных: %s", exception.getMessage());
            log.error(message);
        }
    }
}
