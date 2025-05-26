package com.example.fincoreaccountservice.service;

import com.example.fincoreaccountservice.dto.UserDto;

import java.util.List;

/**
 * Сервис для работы с пользователями
 */
public interface UserService {
    /**
     * Получить всех пользователей
     *
     * @return список DTO пользователей
     */
    List<UserDto> findAll();

    /**
     * Найти пользователя по ID
     *
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    UserDto findById(Integer id);

    /**
     * Создать нового пользователя
     *
     * @param userDto DTO с данными пользователя
     * @return созданный DTO пользователя
     */
    UserDto create(UserDto userDto);

    /**
     * Обновить данные пользователя
     *
     * @param userDto DTO с обновленными данными
     * @return обновленный DTO пользователя
     */
    UserDto update(UserDto userDto);

    /**
     * Удалить пользователя по ID
     *
     * @param id идентификатор пользователя
     */
    void deleteUser(Integer id);
}
