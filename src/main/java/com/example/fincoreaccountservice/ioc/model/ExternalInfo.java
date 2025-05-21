package com.example.fincoreaccountservice.ioc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель данных ExternalInfo
 */
@Data
@AllArgsConstructor
public class ExternalInfo {
    /**
     * Уникальный идентификатор записи
     */
    private Integer id;
    /**
     * Информационное содержимое
     */
    private String info;

    @Override
    public String toString() {
        return "ExternalInfo{id=" + id + ", info=" + info + "}";
    }
}
