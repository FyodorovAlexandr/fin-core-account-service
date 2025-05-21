package com.example.fincoreaccountservice.ioc.service;

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
     */
    String isRunning(ExternalInfo externalInfo);
}
