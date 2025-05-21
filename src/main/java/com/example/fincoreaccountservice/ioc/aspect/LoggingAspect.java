package com.example.fincoreaccountservice.ioc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для логирования начала и окончания выполнения методов в пакете service
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * Логирует начало и окончание выполнения методов в пакете service
     *
     * @param joinPoint точка соединения (join point), содержащая информацию о:
     *                  - целевом методе
     *                  - аргументах вызова
     *                  - целевом объекте
     * @return результат выполнения оригинального метода
     * @throws Throwable если оригинальный метод выбросил исключение
     */
    @Around("execution(* com.example.fincoreaccountservice.ioc.service..*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Start of the method: {}", methodName);

        Object result = joinPoint.proceed();
        log.info("End of method operation: {}", methodName);
        return result;
    }
}
