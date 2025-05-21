package com.example.fincoreaccountservice.ioc.service.impl;

import com.example.fincoreaccountservice.ioc.annotations.CacheResult;
import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import com.example.fincoreaccountservice.ioc.service.ExternalService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса ExternalService с кэшированием и тестовыми данными
 */
@Slf4j
@Service
public class ExternalServiceImpl implements ExternalService {
    private final Map<Integer, ExternalInfo> data = new HashMap<>();

    public ExternalServiceImpl() {
        log.info("Вызов конструктора бина externalServiceImpl");
    }

    /**
     * Инициализация тестовых данных после создания бина
     */
    @PostConstruct
    public void init() {
        data.putAll(Map.of(
                1, new ExternalInfo(1, null),
                2, new ExternalInfo(2, "hasInfo"),
                3, new ExternalInfo(3, "info"),
                4, new ExternalInfo(4, "information")
        ));
        log.info("Инициализация тестовых данных в методе init @PostConstruct бина externalServiceImpl");
    }

    /**
     * Очистка данных перед уничтожением бина.
     */
    @PreDestroy
    public void destroy() {
        log.info("Очистка данных перед уничтожением бина в методе destroy @PreDestroy. HashMap до очистки: {}", data);
        data.clear();
        log.info("HashMap после очистки: {}", data);
    }

    /**
     * Получение ExternalInfo по ID с кэшированием результата.
     *
     * @param id идентификатор информации
     * @return ExternalInfo
     */
    @CacheResult
    @Override
    public ExternalInfo getExternalInfo(Integer id) {
        log.info("Получение ExternalInfo по ID: {} с кэшированием результата", id);
        return data.get(id);
    }
}
