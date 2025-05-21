package com.example.fincoreaccountservice.ioc.service;

import com.example.fincoreaccountservice.ioc.annotations.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * BeanFactoryPostProcessor проверяет prototype-бины с аннотацией @CacheResult
 */
@Slf4j
@Component
public class BeanFactoryPostProcessorService implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("===BeanFactoryPostProcessor===");
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if (beanFactory.getBeanDefinition(beanName).isPrototype()) {
                Class<?> beanClass = beanFactory.getType(beanName);

                Arrays.stream(beanClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(CacheResult.class))
                        .forEach(method -> log.warn("Prototype бин '{}' имеет аннотацию @CacheResult над методом '{}'",
                                beanName, method.getName()));
            }
        }
    }
}
