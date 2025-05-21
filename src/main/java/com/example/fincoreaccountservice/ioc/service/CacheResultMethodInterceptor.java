package com.example.fincoreaccountservice.ioc.service;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация MethodInterceptor для кэширования результатов методов с @CacheResult.
 * <p>
 * Особенности реализации:
 * Использует двухуровневый кэш: по имени метода и аргументам
 * Потокобезопасен благодаря ConcurrentHashMap
 * Корректно обрабатывает null-значения
 * Поддерживает глубокое сравнение массивов аргументов
 */
@Slf4j
public class CacheResultMethodInterceptor implements MethodInterceptor {
    private static final Map<String, Map<CacheKey, Object>> CACHE = new ConcurrentHashMap<>();

    /**
     * Перехватывает вызов метода, реализуя логику кэширования.
     *
     * @param invocation данные о вызове метода
     * @return результат выполнения метода (из кэша или новый)
     * @throws Throwable в случае ошибок выполнения метода
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        CacheKey cacheKey = new CacheKey(invocation.getArguments());

        // Получаем или создаем кэш для конкретного метода
        Map<CacheKey, Object> methodCache = CACHE.computeIfAbsent(methodName, k -> new ConcurrentHashMap<>());

        // Проверяем кэш
        if (methodCache.containsKey(cacheKey)) {
            log.info("Получен кэш для метода {} с аргументом: {}", methodName, cacheKey);
            return methodCache.get(cacheKey);
        }

        // Добавляем в кэш
        Object result = invocation.proceed();
        methodCache.put(cacheKey, result);
        log.info("Добавлен кэш для метода {} с аргументом: {}", methodName, cacheKey);
        return result;
    }

    /**
     * Ключ кэша на основе аргументов метода.
     * Обеспечивает корректное сравнение массивов через Arrays.deepEquals/deepHashCode.
     */
    private static class CacheKey {
        private final Object[] args;
        private final int hashCode;

        CacheKey(Object[] args) {
            this.args = args;
            this.hashCode = Arrays.deepHashCode(args);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return Arrays.deepEquals(args, ((CacheKey) o).args);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return Arrays.deepToString(args);
        }
    }
}