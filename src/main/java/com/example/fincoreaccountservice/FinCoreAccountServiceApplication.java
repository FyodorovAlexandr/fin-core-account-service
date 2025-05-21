package com.example.fincoreaccountservice;

import com.example.fincoreaccountservice.ioc.service.Flow;
import com.example.fincoreaccountservice.ioc.service.TestCachService;
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
        testCacheFunctionality(context);

        context.close();
    }

    private static void testFlow(Flow flow) {
        log.info("=== Testing main flow ===");
        flow.run(1); // info==null - skip processing
        flow.run(2); // normal processing
        flow.run(3); // id-not-process - blocked
        flow.run(4); // normal processing
    }

    private static void testCacheFunctionality(ConfigurableApplicationContext context) {
        log.info("=== Testing cache ===");
        TestCachService testService = context.getBean(TestCachService.class);
        log.info("Result 1: {}", testService.cachedMethod("test1"));
        log.info("Result 2: {}", testService.cachedMethod("test1")); // use cache
    }

}

