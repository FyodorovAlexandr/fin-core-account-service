package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.exception.ProcessException;
import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Сервис для управления потоком обработки данных
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Flow {
    private final ExternalService externalService;
    private final Process process;

    /**
     * Выполняет обработку данных по ID
     *
     * @param id идентификатор данных
     */
    public void run(Integer id) {
        ExternalInfo externalInfo = externalService.getExternalInfo(id);

        if (StringUtils.isNotBlank(externalInfo.getInfo())) {
            try {
                String info = process.isRunning(externalInfo);
                log.info("Результат обработки для ID: {}, info: {}", id, info);
            } catch (ProcessException exception) {
                log.error("Ошибка: {}", exception.getMessage());
            }
        } else {
            log.info("Пропуск обработки для ID {}, info: null", id);
        }
    }
}

