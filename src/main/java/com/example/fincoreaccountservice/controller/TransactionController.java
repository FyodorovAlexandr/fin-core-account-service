package com.example.fincoreaccountservice.controller;

import com.example.fincoreaccountservice.dto.TransactionRequestDto;
import com.example.fincoreaccountservice.dto.TransactionResponse;
import com.example.fincoreaccountservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Контроллер для обработки операций перевода средств
 */
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Перевод средств между банковскими счетами
     *
     * @param request DTO с параметрами перевода
     * @return ResponseEntity с детализированным ответом о статусе операции
     */
    @PostMapping("/between-bank-book")
    public ResponseEntity<TransactionResponse> transferBetweenBankBook(@RequestBody @Valid TransactionRequestDto request) {

        boolean success = transactionService.transferBetweenBankBook(request.getSourceAccountId(),
                request.getTargetAccountId(), request.getAmount());

        return buildResponse(request.getSourceAccountId(), request.getTargetAccountId(), request.getAmount(), success);
    }

    /**
     * Перевод средств между пользователями
     *
     * @param request DTO с параметрами перевода
     * @return ResponseEntity с детализированным ответом о статусе операции
     */
    @PostMapping("/between-users")
    public ResponseEntity<TransactionResponse> transferBetweenUsers(@RequestBody @Valid TransactionRequestDto request) {
        boolean success = transactionService.transferBetweenUser(request.getSourceUserId(), request.getTargetUserId(),
                request.getAmount(), request.getCurrency());

        return buildResponse(request.getSourceUserId(), request.getTargetUserId(), request.getAmount(), success);
    }

    private ResponseEntity<TransactionResponse> buildResponse(Integer sourceId, Integer targetId, BigDecimal amount,
                                                              boolean success) {
        String message = success
                ? String.format("Transfer of %s completed successfully from %s to %s", amount, sourceId, targetId)
                : String.format("Transfer of %s from %s to %s was declined", amount, sourceId, targetId);

        TransactionResponse response = new TransactionResponse(success, message, LocalDateTime.now(), amount, sourceId,
                targetId);

        return ResponseEntity
                .status(success ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }
}