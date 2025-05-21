package com.example.fincoreaccountservice.ioc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Аспект для кэширования результатов методов.
 * Кэширует результаты методов, помеченных аннотацией @CacheResult
 */
@Slf4j
@Aspect
@Component
public class CachingAspect {
    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    /**
     * Реализует логику кэширования результатов методов, помеченных аннотацией @CacheResult
     *
     * @param joinPoint точка соединения для перехвата
     * @param param     параметр метода (используется как часть ключа кэша)
     * @return результат из кэша или результат выполнения метода
     * @throws Throwable в случае ошибок
     */
    @Around("@annotation(com.example.fincoreaccountservice.ioc.annotation.CacheResult) && args(param)")
    public Object cacheMethod(ProceedingJoinPoint joinPoint, Object param) throws Throwable {
        String cacheKey = param.toString();

        if (CACHE.containsKey(cacheKey)) {
            log.info("Получение из кэша: {}", cacheKey);
            return CACHE.get(cacheKey);
        }

        Object result = joinPoint.proceed();
        CACHE.put(cacheKey, result);
        log.info("Добавление в кэш: {}", cacheKey);

        return result;
    }
}
