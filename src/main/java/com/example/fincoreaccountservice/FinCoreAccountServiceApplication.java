package com.example.fincoreaccountservice;

import com.example.fincoreaccountservice.ioc.service.Flow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Главный класс приложения.
 * Инициализирует контекст Spring и выполняет тестовые сценарии.
 */
@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
public class FinCoreAccountServiceApplication {

    /**
     * Точка входа в приложение
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FinCoreAccountServiceApplication.class, args);
        Flow flow = context.getBean(Flow.class);

        testFlow(flow);

        context.close();
    }

    private static void testFlow(Flow flow) {
        log.info("===Запуск проверки основного функционала===");
        flow.run(1); // info=null - пропуск обработки
        flow.run(2); // нормальная обработка
        flow.run(2); // использован кэш @CacheResult через Aspect
        flow.run(3); // id-not-process - блокировка через Aspect
        flow.run(4); // обработка ошибки через Aspect
    }
}

