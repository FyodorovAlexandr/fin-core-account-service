package com.example.fincoreaccountservice.service.impl;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.exception.BankBookNotFoundException;
import com.example.fincoreaccountservice.exception.BankBookNumberChangeException;
import com.example.fincoreaccountservice.exception.DuplicateBankBookException;
import com.example.fincoreaccountservice.exception.InvalidRequestException;
import com.example.fincoreaccountservice.service.BankBookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация сервиса для работы с банковскими счетами с использованием in-memory хранилища
 */
@Service
public class BankBookServiceImpl implements BankBookService {
    private final ConcurrentMap<Integer, BankBookDto> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    /**
     * Получает все счета указанного пользователя
     *
     * @param userId ID пользователя
     * @return Список счетов пользователя
     * @throws InvalidRequestException если userId равен null
     */
    @Override
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        if (userId == null) {
            throw new InvalidRequestException("User ID is required");
        }

        List<BankBookDto> bankBooks = storage.values().stream()
                .filter(bankBookDto -> userId.equals(bankBookDto.getUserId()))
                .toList();

        if (bankBooks.isEmpty()) {
            String message = String.format("Bank book not found with id: %s", userId);
            throw new BankBookNotFoundException(message);
        }

        return bankBooks;
    }

    /**
     * Получает счет по его ID
     *
     * @param bankBookId ID счета
     * @return Найденный счет
     * @throws InvalidRequestException   если bankBookId равен null
     * @throws BankBookNotFoundException если счет не найден
     */
    @Override
    public BankBookDto getBankBookById(Integer bankBookId) {
        if (bankBookId == null) {
            throw new InvalidRequestException("Bank book ID is required");
        }

        return Optional.ofNullable(storage.get(bankBookId))
                .orElseThrow(() -> new BankBookNotFoundException(String.format("Bank book not found with id: %s", bankBookId)));
    }

    /**
     * Создает новый банковский счет
     *
     * @param bankBook Данные для создания счета
     * @return Созданный счет
     * @throws InvalidRequestException    если данные невалидны
     * @throws DuplicateBankBookException если счет с таким номером и валютой уже существует
     */
    @Override
    public BankBookDto createBankBook(BankBookDto bankBook) {
        if (bankBook == null) {
            throw new InvalidRequestException("Bank book data is required");
        }

        if (bankBook.getUserId() == null || bankBook.getNumber() == null ||
                bankBook.getAmount() == null || bankBook.getCurrency() == null) {
            throw new InvalidRequestException("All fields are required");
        }

        boolean isDuplicateExists = storage.values().stream()
                .anyMatch(dto -> bankBook.getUserId().equals(dto.getUserId())
                        && bankBook.getNumber().equals(dto.getNumber())
                        && bankBook.getCurrency().equals(dto.getCurrency()));

        if (isDuplicateExists) {
            String message = String.format("Account with number %s and currency %s already exists for user %d",
                    bankBook.getNumber(), bankBook.getCurrency(), bankBook.getUserId());
            throw new DuplicateBankBookException(message);
        }

        storage.put(idGenerator.getAndIncrement(), bankBook);
        return bankBook;
    }

    /**
     * Обновляет существующий банковский счет
     *
     * @param bankBook Новые данные счета
     * @return Обновленный счет
     * @throws InvalidRequestException       если данные невалидны
     * @throws BankBookNotFoundException     если счет не найден
     * @throws BankBookNumberChangeException при попытке изменить номер счета
     */
    @Override
    public BankBookDto updateBankBook(BankBookDto bankBook) {
        if (bankBook == null) {
            throw new InvalidRequestException("Bank book data is required");
        }

        BankBookDto dto = storage.get(bankBook.getId());

        if (dto == null) {
            throw new BankBookNotFoundException("Bank book not found");
        } else {
            if (!bankBook.getNumber().equals(dto.getNumber())) {
                throw new BankBookNumberChangeException("The account number cannot be changed");
            } else {
                storage.put(bankBook.getId(), bankBook);
            }
        }
        return dto;
    }

    /**
     * Удаляет счет по ID
     *
     * @param bankBookId ID счета для удаления (обязательный)
     * @throws InvalidRequestException   если bankBookId равен null
     * @throws BankBookNotFoundException если счет не найден
     */
    @Override
    public void deleteBankBook(Integer bankBookId) {
        if (bankBookId == null) {
            throw new InvalidRequestException("Bank book ID is required");
        }

        if (storage.remove(bankBookId) == null) {
            String message = String.format("Bank book not found with id: %s", bankBookId);
            throw new BankBookNotFoundException(message);
        }
    }

    /**
     * Удаляет все счета указанного пользователя
     *
     * @param userId ID пользователя (обязательный)
     * @throws InvalidRequestException если userId равен null
     */
    @Override
    public void deleteAllBankBookByUserId(Integer userId) {
        if (userId == null) {
            throw new InvalidRequestException("User ID is required");
        }

        storage.entrySet().removeIf(entry ->
                entry.getValue() != null && userId.equals(entry.getValue().getUserId()));
    }
}
