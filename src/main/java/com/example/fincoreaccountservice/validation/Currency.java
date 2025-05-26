package com.example.fincoreaccountservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Валидатор для проверки существования валюты в системе.
 * Аннотация проверяет, что указанная валюта существует в справочнике валют.
 */
@Constraint(validatedBy = CurrencyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Currency {
    String message() default "Некорректная валюта";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
