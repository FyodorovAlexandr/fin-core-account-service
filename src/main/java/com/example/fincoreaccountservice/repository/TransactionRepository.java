package com.example.fincoreaccountservice.repository;

import com.example.fincoreaccountservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с транзакциями
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
}
