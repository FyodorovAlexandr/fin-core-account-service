package com.example.fincoreaccountservice.validation;

import com.example.fincoreaccountservice.repository.CurrencyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Реализация валидатора для аннотации {@link Currency}.
 * Проверяет существование валюты в базе данных через {@link CurrencyRepository}.
 */
public class CurrencyValidator implements ConstraintValidator<Currency, String> {
    private final CurrencyRepository currencyRepository;

    public CurrencyValidator(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext context) {
        return currencyRepository.findByName(currency).isPresent();
    }
}
