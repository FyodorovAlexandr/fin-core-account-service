package com.example.fincoreaccountservice.repository;

import com.example.fincoreaccountservice.entity.BankBookEntity;
import com.example.fincoreaccountservice.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с банковскими счетами (BankBook) в базе данных.
 */
@Repository
public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {
    List<BankBookEntity> findAllByUserId(Integer userId);

    Optional<BankBookEntity> findByUserIdAndNumberAndCurrency(Integer userId, String number, CurrencyEntity currency);

    Optional<BankBookEntity> findByUserIdAndCurrency(Integer userId, CurrencyEntity currency);

    void deleteAllByUserId(Integer userId);

}