package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.model.ExternalInfo;

/**
 * Интерфейс обработки внешней информации.
 */
public interface Process {
    /**
     * Обрабатывает переданный ExternalInfo.
     *
     * @param externalInfo объект внешней информации
     */
    boolean isRunning(ExternalInfo externalInfo);
}
