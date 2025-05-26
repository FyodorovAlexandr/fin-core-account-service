package com.example.fincoreaccountservice.service.impl;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.exception.BankBookNotFoundException;
import com.example.fincoreaccountservice.exception.BankBookNumberChangeException;
import com.example.fincoreaccountservice.exception.DuplicateBankBookException;
import com.example.fincoreaccountservice.exception.InvalidRequestException;
import com.example.fincoreaccountservice.service.BankBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.fincoreaccountservice.enums.ErrorMessage.*;

/**
 * Реализация сервиса для работы с банковскими счетами с использованием in-memory хранилища
 */
@Slf4j
@Service
public class BankBookServiceImpl implements BankBookService {
    private final ConcurrentMap<Integer, BankBookDto> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    /**
     * Получает все счета указанного пользователя
     *
     * @param userId ID пользователя
     * @return Список счетов пользователя
     * @throws BankBookNotFoundException если счета не найдены
     */
    @Override
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        if (userId == null) {
            throw new BankBookNotFoundException(BANKBOOKS_NOT_FOUND.getMessage());
        }

        List<BankBookDto> bankBooks = storage.values().stream()
                .filter(bankBookDto -> userId.equals(bankBookDto.getUserId()))
                .toList();

        if (bankBooks.isEmpty()) {
            throw new BankBookNotFoundException(BANKBOOKS_NOT_FOUND.getMessage());
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
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }

        return Optional.ofNullable(storage.get(bankBookId))
                .orElseThrow(() -> new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage()));
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
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }

        if (bankBook.getUserId() == null || bankBook.getNumber() == null ||
                bankBook.getAmount() == null || bankBook.getCurrency() == null) {
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }

        boolean isDuplicateExists = storage.values().stream()
                .anyMatch(dto -> bankBook.getUserId().equals(dto.getUserId())
                        && bankBook.getNumber().equals(dto.getNumber())
                        && bankBook.getCurrency().equals(dto.getCurrency()));

        if (isDuplicateExists) {
            throw new DuplicateBankBookException(BANKBOOK_DUPLICATE.getMessage());
        }
        storage.put(idGenerator.getAndIncrement(), bankBook);
        log.info(String.format("Создан счет: %s", bankBook));

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
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }

        BankBookDto dto = storage.get(bankBook.getId());

        if (dto == null) {
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        } else {
            if (!bankBook.getNumber().equals(dto.getNumber())) {
                throw new BankBookNumberChangeException(BANKBOOK_NUMBER_CHANGE.getMessage());
            } else {
                storage.put(bankBook.getId(), bankBook);
                log.info(String.format("Счет обновлен: %s", bankBook));
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
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }

        if (storage.remove(bankBookId) == null) {
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
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
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }

        storage.entrySet().removeIf(entry -> entry.getValue() != null && userId.equals(entry.getValue().getUserId()));
    }
}
