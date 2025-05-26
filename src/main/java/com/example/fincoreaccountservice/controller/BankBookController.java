package com.example.fincoreaccountservice.controller;

import com.example.fincoreaccountservice.dto.BankBookDto;
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
     * Получить все счета пользователя
     *
     * @param userId ID пользователя
     * @return Список счетов пользователя
     */
    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<BankBookDto>> getAllBankBookByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(bankBookService.getAllBankBookByUserId(userId));
    }

    /**
     * Получить счет по ID
     *
     * @param bankBookId ID счета
     * @return Данные счета
     */
    @GetMapping("/{bankBookId}")
    public ResponseEntity<BankBookDto> getBankBookById(@PathVariable Integer bankBookId) {
        return ResponseEntity.ok(bankBookService.getBankBookById(bankBookId));
    }

    /**
     * Создать новый счет
     *
     * @param dto Данные для создания счета
     * @return Созданный счет
     */
    @PostMapping
    public ResponseEntity<BankBookDto> createBankBook(@RequestBody @Valid BankBookDto dto) {
        return ResponseEntity.ok(bankBookService.createBankBook(dto));
    }

    /**
     * Обновить существующий счет
     *
     * @param dto Новые данные счета
     * @return Обновленный счет
     */
    @PutMapping
    public ResponseEntity<BankBookDto> updateBankBook(@RequestBody BankBookDto dto) {
        return ResponseEntity.ok(bankBookService.updateBankBook(dto));
    }

    /**
     * Удалить счет по ID
     *
     * @param bankBookId ID счета для удаления
     */
    @DeleteMapping("/{bankBookId}")
    public ResponseEntity<Void> deleteBankBook(@PathVariable Integer bankBookId) {
        bankBookService.deleteBankBook(bankBookId);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить все счета пользователя
     *
     * @param userId ID пользователя
     */
    @DeleteMapping("/by-user-id/{userId}")
    public ResponseEntity<Void> deleteAllBankBookByUserId(@PathVariable Integer userId) {
        bankBookService.deleteAllBankBookByUserId(userId);
        return ResponseEntity.ok().build();
    }
}
