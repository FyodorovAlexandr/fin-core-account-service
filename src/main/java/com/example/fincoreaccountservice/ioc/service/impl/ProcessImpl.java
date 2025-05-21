package com.example.fincoreaccountservice.ioc.service.impl;

import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import com.example.fincoreaccountservice.ioc.service.Process;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Реализация Process для обработки ExternalInfo с учетом конфигурации исключения по ID.
 */
@Slf4j
@Lazy
@Service
public class ProcessImpl implements Process {

    @Value("${id-not-process}")
    private Integer id;

    /**
     * Обработка ExternalInfo. Возвращает false если ID == 3, иначе true.
     *
     * @param externalInfo объект внешней информации
     */
    @Override
    public boolean isRunning(ExternalInfo externalInfo) {
        if (externalInfo.getId().equals(id)) {
            log.info("ID {} исключен из обработки т.к. равен конфигурационным данным", id);
            return false;
        }
        log.info("Обработка информации для ID {}. Не равен конфигурационным данным", externalInfo.getId());
        return true;
    }
}
