package com.example.fincoreaccountservice;

import com.example.fincoreaccountservice.ioc.service.Flow;
import com.example.fincoreaccountservice.ioc.service.TestCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Главный класс приложения
 */
@Slf4j
@SpringBootApplication
public class FinCoreAccountServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FinCoreAccountServiceApplication.class, args);
        Flow flow = context.getBean(Flow.class);

        // 1. Проверка основного функционала
        log.info("===Запуск проверки основного функционала===");
        flow.run(1); // info=null - пропуск обработки
        flow.run(2); // нормальная обработка
        flow.run(2); // использование кэша
        flow.run(3); // id-not-process - возвращает false
        flow.run(4); // нормальная обработка

        // 2. Проверка prototype-бинов с @CacheResult
        log.info("===Запуск проверки prototype бинов с @CacheResult===");
        TestCacheService test1 = context.getBean(TestCacheService.class);
        TestCacheService test2 = context.getBean(TestCacheService.class);

        log.info("Результат первого prototype бина: {}", test1.getData("first"));
        log.info("Результат второго prototype бина:: {}", test2.getData("second"));
        log.info("Результат кэширования первого prototype бина: {}", test1.getData("first")); // использование кэша

        context.close();
    }
}
