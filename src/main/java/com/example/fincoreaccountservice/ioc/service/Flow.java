package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.exception.NotFoundException;
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
        try {
            ExternalInfo info = externalService.getExternalInfo(id);

            if (StringUtils.isNotBlank(info.getInfo())) {
                boolean hasRunning = process.isRunning(info);
                log.info("Processing result for ID {}: {}", id, hasRunning);
            } else {
                log.info("Skipping processing for ID {} (info is null)", id);
            }
        } catch (NotFoundException e) {
            log.error("Error processing ID {}: {}", id, e.getMessage());
        }
    }
}
