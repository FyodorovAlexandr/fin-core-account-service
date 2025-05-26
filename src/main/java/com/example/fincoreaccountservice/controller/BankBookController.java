package com.example.fincoreaccountservice.controller;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.dto.ErrorDto;
import com.example.fincoreaccountservice.exception.BankBookNotFoundException;
import com.example.fincoreaccountservice.exception.BankBookNumberChangeException;
import com.example.fincoreaccountservice.exception.DuplicateBankBookException;
import com.example.fincoreaccountservice.exception.InvalidRequestException;
import com.example.fincoreaccountservice.service.BankBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с банковскими счетами
 */
@RestController
@RequestMapping("/bank-book")
@RequiredArgsConstructor
@Validated
public class BankBookController {
    private final BankBookService bankBookService;

    /**
     * Получить счета пользователя
     *
     * @param userId ID пользователя
     * @return Ответ со списком счетов или ошибкой
     */
    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<?> getAllBankBookByUserId(@PathVariable Integer userId) {
        try {
            List<BankBookDto> bankBooks = bankBookService.getAllBankBookByUserId(userId);
            return ResponseEntity.ok(bankBooks);
        } catch (InvalidRequestException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.MISSING_USER_ID);
        }
    }

    /**
     * Получить счет по ID
     *
     * @param bankBookId ID счета
     * @return Ответ с данными счета или ошибкой
     */
    @GetMapping("/{bankBookId}")
    public ResponseEntity<?> getBankBookById(@PathVariable Integer bankBookId) {
        try {
            BankBookDto bankBook = bankBookService.getBankBookById(bankBookId);
            return ResponseEntity.ok(bankBook);
        } catch (InvalidRequestException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.MISSING_BANKBOOK_ID);
        } catch (BankBookNotFoundException exception) {
            return ResponseEntity.status(404).body(ErrorDto.NOT_FOUND);
        }
    }

    /**
     * Создать новый счет
     *
     * @param dto Данные нового счета
     * @return Ответ с созданным счетом или ошибкой
     */
    @PostMapping
    public ResponseEntity<?> createBankBook(@RequestBody @Valid BankBookDto dto) {
        try {
            BankBookDto created = bankBookService.createBankBook(dto);
            return ResponseEntity.ok(created);
        } catch (InvalidRequestException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.INVALID_REQUEST);
        } catch (DuplicateBankBookException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.DUPLICATE_ACCOUNT);
        }
    }

    /**
     * Обновить существующий счет
     *
     * @param dto Новые данные счета
     * @return Ответ с обновленным счетом или ошибкой
     */
    @PutMapping
    public ResponseEntity<?> updateBankBook(@RequestBody @Valid BankBookDto dto) {
        try {
            BankBookDto updated = bankBookService.updateBankBook(dto);
            return ResponseEntity.ok(updated);
        } catch (InvalidRequestException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.INVALID_REQUEST);
        } catch (BankBookNotFoundException exception) {
            return ResponseEntity.status(404).body(ErrorDto.NOT_FOUND);
        } catch (BankBookNumberChangeException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.NUMBER_CHANGE);
        }
    }

    /**
     * Удалить счет по ID
     *
     * @param bankBookId ID счета (обязательный)
     * @return Пустой ответ или ошибка
     */
    @DeleteMapping("/{bankBookId}")
    public ResponseEntity<?> deleteBankBook(@PathVariable Integer bankBookId) {
        try {
            bankBookService.deleteBankBook(bankBookId);
            return ResponseEntity.ok().build();
        } catch (InvalidRequestException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.MISSING_BANKBOOK_ID);
        } catch (BankBookNotFoundException exception) {
            return ResponseEntity.status(404).body(ErrorDto.NOT_FOUND);
        }
    }

    /**
     * Удалить все счета пользователя
     *
     * @param userId ID пользователя
     * @return Пустой ответ или ошибка
     */
    @DeleteMapping("/by-user-id/{userId}")
    public ResponseEntity<?> deleteAllBankBookByUserId(@PathVariable Integer userId) {
        try {
            bankBookService.deleteAllBankBookByUserId(userId);
            return ResponseEntity.ok().build();
        } catch (InvalidRequestException exception) {
            return ResponseEntity.badRequest().body(ErrorDto.MISSING_USER_ID);
        }
    }
}
