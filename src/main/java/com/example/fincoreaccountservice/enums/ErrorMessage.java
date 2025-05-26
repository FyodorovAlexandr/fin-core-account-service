package com.example.fincoreaccountservice.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    BANKBOOK_NOT_FOUND("Счет не найден"),
    BANKBOOKS_NOT_FOUND("Счета не найдены"),
    BANKBOOK_DUPLICATE("Счет с таким номером и валютой уже существует"),
    BANKBOOK_NUMBER_CHANGE("Номер счета изменить нельзя"),
    CURRENCY_NOT_FOUND("Валюта не найдена"),
    CURRENCY_MISMATCH("Несоответствие валют между счетами"),
    STATUS_NOT_FOUND("Статус не найден"),
    USER_NOT_FOUND("Пользователь не найден"),
    USERS_NOT_FOUND("Пользователи не найдены"),
    EMAIL_DUPLICATE("Данный email уже существует");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}