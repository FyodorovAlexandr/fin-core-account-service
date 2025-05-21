package com.example.fincoreaccountservice.ioc.service.impl;

import com.example.fincoreaccountservice.ioc.annotation.CheckRequest;
import com.example.fincoreaccountservice.ioc.exception.ProcessException;
import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import com.example.fincoreaccountservice.ioc.service.Process;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Реализация обработчика данных с проверкой запросов
 */
@Slf4j
@Lazy
@Service
public class ExternalInfoProcess implements Process {

    @Value("${id-not-process}")
    private Integer id;

    /**
     * Обрабатывает внешнюю информацию с проверкой через аспект.
     * Метод помечен аннотацией @CheckRequest, активирует проверку в CheckRequestAspect.
     *
     * @param externalInfo данные для обработки
     * @return true если обработка выполнена успешно
     * @throws ProcessException если ID == 3
     */
    @CheckRequest
    @Override
    public boolean isRunning(ExternalInfo externalInfo) {

        if (externalInfo.getId().equals(id)) {
            throw new ProcessException("Processing not allowed for ID: " + id);
        } else {
            return true;
        }
    }
}
