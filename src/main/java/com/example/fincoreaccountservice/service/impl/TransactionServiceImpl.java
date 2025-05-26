package com.example.fincoreaccountservice.service.impl;

import com.example.fincoreaccountservice.entity.BankBookEntity;
import com.example.fincoreaccountservice.entity.CurrencyEntity;
import com.example.fincoreaccountservice.entity.StatusEntity;
import com.example.fincoreaccountservice.entity.TransactionEntity;
import com.example.fincoreaccountservice.enums.Status;
import com.example.fincoreaccountservice.exception.BankBookNotFoundException;
import com.example.fincoreaccountservice.exception.CurrencyException;
import com.example.fincoreaccountservice.exception.CurrencyNotEqualsException;
import com.example.fincoreaccountservice.repository.BankBookRepository;
import com.example.fincoreaccountservice.repository.CurrencyRepository;
import com.example.fincoreaccountservice.repository.StatusRepository;
import com.example.fincoreaccountservice.repository.TransactionRepository;
import com.example.fincoreaccountservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для выполнения банковских транзакций.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final BankBookRepository bankBookRepository;
    private final StatusRepository statusRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;

    /**
     * Перевод между счетами
     */
    @Override
    @Transactional
    public boolean transferBetweenBankBook(Integer sourceAccountId, Integer targetAccountId, BigDecimal amount) {
        // Валидация параметров
        validateTransferParameters(amount, sourceAccountId, targetAccountId);

        // Получение счетов с проверкой существования
        BankBookEntity sourceAccount = bankBookRepository.findById(sourceAccountId)
                .orElseThrow(() -> new BankBookNotFoundException("Source account not found with id: " + sourceAccountId));

        BankBookEntity targetAccount = bankBookRepository.findById(targetAccountId)
                .orElseThrow(() -> new BankBookNotFoundException("Target account not found with id: " + targetAccountId));

        // Проверка валют
        if (!sourceAccount.getCurrency().getId().equals(targetAccount.getCurrency().getId())) {
            registerTransaction(sourceAccount, targetAccount, amount, Status.DECLINED);
            throw new CurrencyNotEqualsException(String.format("Currency mismatch. Source: %s, Target: %s",
                    sourceAccount.getCurrency().getName(),
                    targetAccount.getCurrency().getName()));
        }

        // Проверка баланса
        if (sourceAccount.getAmount().compareTo(amount) < 0) {
            registerTransaction(sourceAccount, targetAccount, amount, Status.DECLINED);
            log.warn("Insufficient funds in account {} (balance: {}, required: {})",
                    sourceAccountId, sourceAccount.getAmount(), amount);
            return false;
        }

        // Выполнение перевода
        performTransfer(sourceAccount, targetAccount, amount);
        registerTransaction(sourceAccount, targetAccount, amount, Status.SUCCESSFUL);
        log.info("Transfer completed: {} {} from id {} to id {}",
                amount, sourceAccount.getCurrency().getName(), sourceAccountId, targetAccountId);

        return true;
    }

    /**
     * Перевод между пользователями
     */
    @Override
    @Transactional
    public boolean transferBetweenUser(Integer sourceUserId, Integer targetUserId, BigDecimal amount, String currencyCode) {
        // Валидация параметров
        validateTransferParameters(amount, sourceUserId, targetUserId);
        if (currencyCode == null || currencyCode.length() != 3) {
            throw new IllegalArgumentException("Currency code must be 3 characters");
        }

        // Поиск валюты
        CurrencyEntity currency = currencyRepository.findByName(currencyCode.toUpperCase())
                .orElseThrow(() -> new CurrencyException("Currency not supported: " + currencyCode));

        // Поиск счетов пользователей
        BankBookEntity sourceAccount = bankBookRepository.findByUserIdAndCurrency(sourceUserId, currency)
                .orElseThrow(() -> new BankBookNotFoundException(
                        String.format("User %d has no account in currency %s", sourceUserId, currencyCode)));

        BankBookEntity targetAccount = bankBookRepository.findByUserIdAndCurrency(targetUserId, currency)
                .orElseThrow(() -> new BankBookNotFoundException(
                        String.format("User %d has no account in currency %s", targetUserId, currencyCode)));

        // Делегируем выполнение перевода между счетами
        return transferBetweenBankBook(sourceAccount.getId(), targetAccount.getId(), amount);
    }

    private void validateTransferParameters(BigDecimal amount, Integer sourceId, Integer targetId) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (sourceId == null || targetId == null) {
            throw new IllegalArgumentException("Account/user IDs cannot be null");
        }
        if (sourceId.equals(targetId)) {
            throw new IllegalArgumentException("Source and target must be different");
        }
    }

    private void performTransfer(BankBookEntity source, BankBookEntity target, BigDecimal amount) {
        source.setAmount(source.getAmount().subtract(amount));
        target.setAmount(target.getAmount().add(amount));
        bankBookRepository.saveAll(List.of(source, target));
    }

    private void registerTransaction(BankBookEntity source, BankBookEntity target, BigDecimal amount, Status status) {
        StatusEntity statusEntity = statusRepository.findByName(status.getStatus())
                .orElseThrow(() -> new IllegalStateException("Invalid status: " + status));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setSourceBankBook(source);
        transaction.setTargetBankBook(target);
        transaction.setAmount(amount);
        transaction.setStatus(statusEntity);
        transaction.setInitiationDate(LocalDateTime.now());
        transaction.setCompletionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}