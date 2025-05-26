package com.example.fincoreaccountservice.service.impl;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.entity.BankBookEntity;
import com.example.fincoreaccountservice.entity.CurrencyEntity;
import com.example.fincoreaccountservice.exception.BankBookNotFoundException;
import com.example.fincoreaccountservice.exception.BankBookNumberChangeException;
import com.example.fincoreaccountservice.exception.CurrencyException;
import com.example.fincoreaccountservice.exception.DuplicateBankBookException;
import com.example.fincoreaccountservice.mapper.BankBookMapper;
import com.example.fincoreaccountservice.repository.BankBookRepository;
import com.example.fincoreaccountservice.repository.CurrencyRepository;
import com.example.fincoreaccountservice.service.BankBookService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.fincoreaccountservice.enums.ErrorMessage.*;

/**
 * Реализация сервиса для работы с банковскими счетами с использованием in-memory хранилища
 */
@RequiredArgsConstructor
@Transactional
@Service
public class BankBookServiceImpl implements BankBookService {
    private final BankBookRepository bankBookRepository;
    private final CurrencyRepository currencyRepository;
    private final BankBookMapper bankBookMapper;

    /**
     * Получает все счета указанного пользователя
     *
     * @param userId ID пользователя
     * @return Список счетов пользователя
     * @throws BankBookNotFoundException если счет не найден
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        List<BankBookDto> bankBooks = bankBookRepository.findAllByUserId(userId).stream()
                .map(bankBookMapper::toDto)
                .toList();

        if (CollectionUtils.isEmpty(bankBooks)) {
            throw new BankBookNotFoundException(BANKBOOKS_NOT_FOUND.getMessage());
        }

        return bankBooks;
    }

    /**
     * Получает счет по его ID
     *
     * @param bankBookId ID счета
     * @return Найденный счет
     * @throws BankBookNotFoundException если счет не найден
     */
    @Override
    @Transactional(readOnly = true)
    public BankBookDto getBankBookById(Integer bankBookId) {
        return bankBookRepository.findById(bankBookId)
                .map(bankBookMapper::toDto)
                .orElseThrow(() -> new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage()));
    }

    /**
     * Создает новый банковский счет
     *
     * @param dto Данные для создания счета
     * @return Созданный счет
     * @throws CurrencyException  если валюта не найдена
     * @throws DuplicateBankBookException если счет с таким номером и валютой уже существует
     */
    @Override
    public BankBookDto createBankBook(BankBookDto dto) {
        CurrencyEntity currency = currencyRepository.findByName(dto.getCurrency())
                .orElseThrow(() -> new CurrencyException(CURRENCY_NOT_FOUND.getMessage()));

        Optional<BankBookEntity> bankBookEntityOptional = bankBookRepository.findByUserIdAndNumberAndCurrency(
                dto.getUserId(), dto.getNumber(), currency);

        if (bankBookEntityOptional.isPresent()) {
            throw new DuplicateBankBookException(BANKBOOK_DUPLICATE.getMessage());
        }

        BankBookEntity bankBookEntity = bankBookMapper.toEntity(dto);
        bankBookEntity.setCurrency(currency);

        return bankBookMapper.toDto(bankBookRepository.save(bankBookEntity));
    }

    /**
     * Обновляет существующий банковский счет
     *
     * @param dto Новые данные счета
     * @return Обновленный счет
     * @throws BankBookNotFoundException     если счет не найден
     * @throws CurrencyException     если валюта не найдена
     * @throws BankBookNumberChangeException при попытке изменить номер счета
     */
    @Override
    public BankBookDto updateBankBook(BankBookDto dto) {
        BankBookEntity bankBookEntity = bankBookRepository.findById(dto.getId())
                .orElseThrow(() -> new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage()));

        if (!bankBookEntity.getNumber().equals(dto.getNumber())) {
            throw new BankBookNumberChangeException(BANKBOOK_NUMBER_CHANGE.getMessage());
        }

        CurrencyEntity currency = currencyRepository.findByName(dto.getCurrency())
                .orElseThrow(() -> new CurrencyException(CURRENCY_NOT_FOUND.getMessage()));

        bankBookEntity = bankBookMapper.toEntity(dto);
        bankBookEntity.setCurrency(currency);

        return bankBookMapper.toDto(bankBookRepository.save(bankBookEntity));
    }

    /**
     * Удаляет счет по ID
     *
     * @param bankBookId ID счета для удаления
     * @throws BankBookNotFoundException если счет не найден
     */
    @Override
    public void deleteBankBook(Integer bankBookId) {
        if (!bankBookRepository.existsById(bankBookId)) {
            throw new BankBookNotFoundException(BANKBOOK_NOT_FOUND.getMessage());
        }
        bankBookRepository.deleteById(bankBookId);
    }

    /**
     * Удаляет все счета указанного пользователя
     *
     * @param userId ID пользователя
     * @throws BankBookNotFoundException если счет не найден
     */
    @Override
    public void deleteAllBankBookByUserId(Integer userId) {
        List<BankBookEntity> bankBookEntities = bankBookRepository.findAllByUserId(userId);

        if (CollectionUtils.isEmpty(bankBookEntities)) {
            throw new BankBookNotFoundException(BANKBOOKS_NOT_FOUND.getMessage());
        }
        bankBookRepository.deleteAllByUserId(userId);
    }
}
