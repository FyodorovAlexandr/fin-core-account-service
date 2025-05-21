package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.exception.NotFoundException;
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
     * @throws NotFoundException если данные не найдены
     */
    ExternalInfo getExternalInfo(Integer id) throws NotFoundException;
}
