package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.model.ExternalInfo;

/**
 * Интерфейс для получения ExternalInfo по идентификатору.
 */
public interface ExternalService {
    /**
     * Получает ExternalInfo по заданному идентификатору.
     *
     * @param id идентификатор информации
     * @return ExternalInfo или null, если не найдено
     */
    ExternalInfo getExternalInfo(Integer id);
}
