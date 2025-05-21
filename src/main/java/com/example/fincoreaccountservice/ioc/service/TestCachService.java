package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Тестовый сервис для демонстрации кэширования CachingAspect
 */
@Slf4j
@Component
@Scope("prototype")
public class TestCachService {

    /**
     * Метод с кэшированием результатов через CachingAspect
     *
     * @param param входной параметр
     * @return обработанный результат
     */
    @CacheResult
    public String cachedMethod(String param) {
        log.info("Executing method with param: " + param);
        return "Processed: " + param;
    }
}
