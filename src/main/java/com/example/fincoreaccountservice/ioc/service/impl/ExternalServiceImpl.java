package com.example.fincoreaccountservice.ioc.service.impl;

import com.example.fincoreaccountservice.ioc.exception.NotFoundException;
import com.example.fincoreaccountservice.ioc.model.ExternalInfo;
import com.example.fincoreaccountservice.ioc.service.ExternalService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация сервиса для работы с внешними данными
 */
@Slf4j
@Service
public class ExternalServiceImpl implements ExternalService {
    private static final Map<Integer, ExternalInfo> DATA = new ConcurrentHashMap<>();

    /**
     * Инициализация тестовых данных
     */
    @PostConstruct
    public void init() {
        DATA.putAll(Map.of(
                1, new ExternalInfo(1, null),
                2, new ExternalInfo(2, "hasInfo"),
                3, new ExternalInfo(3, "info"),
                4, new ExternalInfo(4, "information")
        ));
    }

    /**
     * Очистка данных перед закрытием контекста
     */
    @PreDestroy
    public void destroy() {
        log.info("Data cleared before context shutdown");
        log.info("HashMap before cleared: {}", DATA);
        DATA.clear();
        log.info("HashMap after cleared: {}", DATA);
    }

    @Override
    public ExternalInfo getExternalInfo(Integer id) {
        return Optional.ofNullable(DATA.get(id))
                .orElseThrow(() -> new NotFoundException("Not found info for ID: " + id));
    }
}
