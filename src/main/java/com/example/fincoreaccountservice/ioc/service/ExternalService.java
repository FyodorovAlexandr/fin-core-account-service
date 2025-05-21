package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.model.ExternalInfo;

/**
 * Интерфейс сервиса для работы с ExternalInfo
 */
public interface ExternalService {
    /**
     * Получает ExternalInfo по ID
     *
     * @param id идентификатор записи
     * @return объект ExternalInfo
     */
    ExternalInfo getExternalInfo(Integer id);
}
