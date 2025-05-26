package com.example.fincoreaccountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для адреса
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    /**
     * Страна пользователя
     */
    @NotNull(message = "Страна не может быть null")
    private String country;
    /**
     * Город пользователя
     */
    @NotNull(message = "Город не может быть null")
    private String city;
    /**
     * Улица пользователя
     */
    private String street;
    /**
     * Дом пользователя
     */
    private String home;
}
