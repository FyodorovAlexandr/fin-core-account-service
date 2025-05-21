package com.example.fincoreaccountservice.ioc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для централизованной обработки исключений в пакете service
 */
@Slf4j
@Aspect
@Component
public class ErrorHandlingAspect {

    /**
     * Обрабатывает исключения, возникшие в методах сервисов
     *
     * @param joinPoint точка соединения, где произошло исключение
     * @param exception перехваченное исключение
     */
    @AfterThrowing(
            pointcut = "execution(* com.example.fincoreaccountservice.ioc.service..*(..))",
            throwing = "exception"
    )
    public void handleError(JoinPoint joinPoint, Throwable exception) {
        log.error("Error in {}: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
