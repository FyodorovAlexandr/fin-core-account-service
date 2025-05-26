package com.example.fincoreaccountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * ID пользователя
     */
    private Integer id;

    /**
     * Имя пользователя
     */
    @NotNull(message = "Name не может быть null")
    private String name;

    /**
     * Email пользователя
     */
    @Email
    @NotNull(message = "Email не может быть null")
    private String email;

    /**
     * Адрес пользователя
     */
    @NotNull(message = "Address не может быть null")
    private AddressDto address;
}
