package com.example.fincoreaccountservice.ioc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель данных ExternalInfo представляет информацию, полученную из внешнего сервиса
 */
@Data
@AllArgsConstructor
public class ExternalInfo {
    /**
     * Идентификатор информации.
     */
    private Integer id;
    /**
     * Дополнительная информация.
     */
    private String info;

    @Override
    public String toString() {
        return "ExternalInfo{id=" + id + ", info=" + info + "}";
    }
}
