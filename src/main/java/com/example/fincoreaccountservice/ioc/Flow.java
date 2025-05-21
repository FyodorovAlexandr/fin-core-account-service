package com.example.fincoreaccountservice.ioc;

import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import com.example.fincoreaccountservice.ioc.service.ExternalService;
import com.example.fincoreaccountservice.ioc.service.Process;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Класс для получения и обработки внешней информации по ID
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Flow {
    private final ExternalService externalService;
    private final Process process;

    /**
     * Выполняет основную логику: получение данных, обработку или логирование пропуска.
     *
     * @param id идентификатор внешней информации для обработки
     */
    public void run(Integer id) {
        ExternalInfo externalInfo = externalService.getExternalInfo(id);

        if (externalInfo.getInfo() != null) {
            boolean isResult = process.isRunning(externalInfo);
            log.info("Processing result for ID {}: {}", externalInfo.getId(), isResult);
        } else {
            log.info("Skipping processing for ID {} (info is null)", id);
        }
    }
}
