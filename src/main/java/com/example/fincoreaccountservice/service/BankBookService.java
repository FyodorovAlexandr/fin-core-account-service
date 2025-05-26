package com.example.fincoreaccountservice.service;

import com.example.fincoreaccountservice.dto.BankBookDto;

import java.util.List;

/**
 * Сервис для работы с банковскими счетами
 */
public interface BankBookService {
    /**
     * Получить все счета пользователя
     *
     * @param userId ID пользователя
     * @return Список счетов пользователя
     */
    List<BankBookDto> getAllBankBookByUserId(Integer userId);

    /**
     * Получить счет по ID
     *
     * @param bankBookId ID счета
     * @return Данные счета
     */
    BankBookDto getBankBookById(Integer bankBookId);

    /**
     * Создать новый счет
     *
     * @param dto Данные для создания счета
     * @return Созданный счет
     */
    BankBookDto createBankBook(BankBookDto dto);

    /**
     * Обновить существующий счет
     *
     * @param dto Новые данные счета
     * @return Обновленный счет
     */
    BankBookDto updateBankBook(BankBookDto dto);

    /**
     * Удалить счет по ID
     *
     * @param bankBookId ID счета
     */
    void deleteBankBook(Integer bankBookId);

    /**
     * Удалить все счета пользователя
     *
     * @param userId ID пользователя
     */
    void deleteAllBankBookByUserId(Integer userId);
}
