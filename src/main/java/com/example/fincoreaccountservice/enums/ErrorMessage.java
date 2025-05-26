package com.example.fincoreaccountservice.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    BANKBOOK_NOT_FOUND("Счет не найден"),
    BANKBOOKS_NOT_FOUND("Счета не найдены"),
    BANKBOOK_DUPLICATE("Счет с таким номером и валютой уже существует"),
    BANKBOOK_NUMBER_CHANGE("Номер счета изменить нельзя");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
