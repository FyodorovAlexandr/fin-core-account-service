package com.example.fincoreaccountservice.ioc.service.impl;

import com.example.fincoreaccountservice.ioc.annotation.CheckRequest;
import com.example.fincoreaccountservice.ioc.exception.ProcessException;
import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import com.example.fincoreaccountservice.ioc.service.Process;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Реализация обработчика данных с проверкой запросов
 */
@Slf4j
@Service
public class ProcessImpl implements Process {

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
    public String isRunning(ExternalInfo externalInfo) throws ProcessException {
        Integer id = 4;
        if (externalInfo.getId().equals(id)) {
            throw new ProcessException("Запрещена обработка для ID == " + id);
        } else {
            return externalInfo.getInfo();
        }
    }
}
