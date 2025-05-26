package com.example.fincoreaccountservice.service.impl;

import com.example.fincoreaccountservice.dto.UserDto;
import com.example.fincoreaccountservice.entity.UserEntity;
import com.example.fincoreaccountservice.exception.DuplicateEmailException;
import com.example.fincoreaccountservice.exception.UserException;
import com.example.fincoreaccountservice.mapper.UserMapper;
import com.example.fincoreaccountservice.repository.UserRepository;
import com.example.fincoreaccountservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.fincoreaccountservice.enums.ErrorMessage.*;

/**
 * Реализация сервиса для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Получить всех пользователей
     *
     * @return список DTO пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<UserDto> users = userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();

        if (CollectionUtils.isEmpty(users)) {
            throw new UserException(USERS_NOT_FOUND.getMessage());
        }
        return users;
    }

    /**
     * Получение пользователя по ID
     *
     * @param id ID пользователя
     * @return список пользователей
     * @throws UserException если пользователь не найден
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND.getMessage()));
    }

    /**
     * Создание нового пользователя
     *
     * @param userDto DTO с данными пользователя
     * @return созданный DTO пользователя
     */
    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateEmailException(EMAIL_DUPLICATE.getMessage());
        }

        UserEntity entity = userMapper.toEntity(userDto);
        UserEntity saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    /**
     * Обновление данных пользователя
     *
     * @param userDto DTO с обновленными данными
     * @return обновленный DTO пользователя
     * @throws UserException если пользователь не найден
     */
    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        UserEntity entity = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND.getMessage()));

        userMapper.updateEntityFromDto(userDto, entity);
        return userMapper.toDto(userRepository.save(entity));
    }

    /**
     * Удаление пользователя
     *
     * @param id ID пользователя
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserException(USER_NOT_FOUND.getMessage());
        }
        userRepository.deleteById(id);
    }
}