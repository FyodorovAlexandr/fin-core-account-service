package com.example.fincoreaccountservice.service;

import java.math.BigDecimal;

/**
 * Сервис для выполнения транзакций
 */
public interface TransactionService {
    /**
     * Перевод между счетами
     *
     * @param sourceAccountId Счет-источник
     * @param targetAccountId Счет-получатель
     * @param amount          Сумма перевода
     * @return true если перевод успешен, false если нет
     */
    boolean transferBetweenBankBook(Integer sourceAccountId, Integer targetAccountId, BigDecimal amount);

    /**
     * Перевод между пользователями
     *
     * @param sourceUserId Пользователь-источник
     * @param targetUserId Пользователь-получатель
     * @param amount       Сумма перевода
     * @param currency     Валюта перевода
     * @return true если перевод успешен, false если нет
     */
    boolean transferBetweenUser(Integer sourceUserId, Integer targetUserId, BigDecimal amount, String currency);
}
