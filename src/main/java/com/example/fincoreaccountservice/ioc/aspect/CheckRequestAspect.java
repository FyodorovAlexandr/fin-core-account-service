package com.example.fincoreaccountservice.ioc.aspect;

import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Аспект для проверки запросов перед выполнением методов
 */
@Slf4j
@Aspect
@Component
public class CheckRequestAspect {

    @Value("${id-not-process}")
    private Integer id;

    /**
     * Проверяет запрос перед выполнением метода. Запросы с определенным ID не должны обрабатываться.
     * Использует аннотацию @CheckRequest для определения методов, требующих проверки.
     *
     * @param joinPoint    точка соединения для перехвата
     * @param externalInfo проверяемый объект запроса
     * @return false если запрос заблокирован (id==3), иначе результат целевого метода
     * @throws Throwable в случае ошибок
     */
    @Around("@annotation(com.example.fincoreaccountservice.ioc.annotation.CheckRequest) && args(externalInfo)")
    public Object checkRequest(ProceedingJoinPoint joinPoint, ExternalInfo externalInfo) throws Throwable {

        if (externalInfo.getId().equals(id)) {
            log.warn("Блокировка запроса с ID: {}", id);
            return "ID совпадает с конфигурационными данными";
        }
        return joinPoint.proceed();
    }
}
