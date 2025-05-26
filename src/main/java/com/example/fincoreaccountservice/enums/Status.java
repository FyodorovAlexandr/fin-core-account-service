package com.example.fincoreaccountservice.enums;

/**
 * Статусы транзакций
 */
public enum Status {
    PROCESSING("processing"),
    SUCCESSFUL("successful"),
    DECLINED("declined");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
