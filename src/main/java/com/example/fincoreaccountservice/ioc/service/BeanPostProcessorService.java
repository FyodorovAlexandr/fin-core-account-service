package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.annotations.CacheResult;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Реализация BeanPostProcessor для создания прокси-объектов вокруг методов, помеченных аннотацией @CacheResult.
 * <p>
 * Основные особенности:
 * Создает динамический прокси для каждого бина, содержащего методы с @CacheResult
 * Добавляет CacheResultMethodInterceptor для перехвата вызовов методов
 * Работает только с singleton-бинами (prototype бины пропускаются)
 */
@Slf4j
@Component
public class BeanPostProcessorService implements BeanPostProcessor {

    /**
     * Обрабатывает бин перед инициализацией.
     *
     * @param bean     обрабатываемый бин
     * @param beanName имя бина в контексте
     * @return исходный бин без изменений
     */
    @Nonnull
    @Override
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        log.info("===BeanPostProcessor===");
        log.info("Обработка бина '{}' до инициализации:", beanName);
        return bean;
    }

    /**
     * Обрабатывает бин после инициализации, создавая прокси при необходимости.
     *
     * @param bean     инициализированный бин
     * @param beanName имя бина в контексте
     * @return оригинальный бин или прокси-объект с кэшированием
     * @throws BeansException в случае ошибок обработки
     */
    @Nonnull
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        log.info("Обработка бина '{}' после инициализации:", beanName);
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CacheResult.class)) {
                log.info("Создание прокси для бина: '{}' у которого @CacheResult над методом '{}' ", beanName, method.getName());

                ProxyFactory proxyFactory = new ProxyFactory(bean);
                proxyFactory.addAdvice(new CacheResultMethodInterceptor());
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }
}
