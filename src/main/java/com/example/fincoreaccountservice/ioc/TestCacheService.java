package com.example.fincoreaccountservice.ioc;

import com.example.fincoreaccountservice.ioc.annotations.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Тестовый сервис для демонстрации работы кэширования на prototype-бинах
 */
@Slf4j
@Component
@Scope("prototype")
public class TestCacheService {
    private static int instanceCounter = 0;
    private final int instanceId;

    public TestCacheService() {
        this.instanceId = ++instanceCounter;
        log.info("Created TestCacheService instance #{}", instanceId);
    }

    @CacheResult
    public String getData(String param) {
        String result = "Processed: " + param;
        log.info("Instance {} processing param: '{}', result: '{}'", instanceId, param, result);
        return result;
    }
}