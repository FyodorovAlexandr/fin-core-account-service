package com.example.fincoreaccountservice.repository;

import com.example.fincoreaccountservice.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с валютами (Currency) в базе данных.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {
    Optional<CurrencyEntity> findByName(String name);
}
