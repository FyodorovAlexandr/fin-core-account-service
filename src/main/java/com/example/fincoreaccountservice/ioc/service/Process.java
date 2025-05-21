package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.exception.ProcessException;
import com.example.fincoreaccountservice.ioc.model.ExternalInfo;

/**
 * Интерфейс для обработки данных
 */
public interface Process {
    /**
     * Выполняет обработку данных
     *
     * @param externalInfo данные для обработки
     * @return результат обработки
     * @throws ProcessException при ошибках обработки данных
     */
    boolean isRunning(ExternalInfo externalInfo) throws ProcessException;
}
