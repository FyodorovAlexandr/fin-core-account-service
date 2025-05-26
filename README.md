# Spring REST: Optimized CRUD with JPA and PostgreSQL

Проект демонстрирует RESTful API для банковской системы, реализующей основные операции по управлению пользователями,
банковскими счетами и транзакциями при помощи PostgreSQL.

## Основная функциональность

- Добавлена Spring Data JPA и работа с репозиториями.
- Добавлена валидация для DTO.
- Добавлен маппинг между DTO и Entity через MapStruct.
- Добавлены сущности: BankBookEntity, CurrencyEntity, UserEntity, AddressEntity, TransactionEntity, StatusEntity,
  GroupEntity.
- Таблицы созданы с помощью Liquibase. Добавлены все необходимые связи.
- Добавлено развертывание PostgreSQL в Docker
- Добавлен глобальный обработчик исключений
- Добавлена кастомная валидация через аннотацию @Currency которая проверяет существование валюты в БД
- Логирование всех операций

## Технологический стек

- **Язык**: Java 21
- **Фреймворк**: Spring Boot 3.x
- **Сборка**: Maven
- **Spring Core**: DI, beans, context
- **Spring AOP**: Exception handling
- **Spring Web**: REST API
- **Spring Validation**: Data validation
- **Spring Data JPA**: Entity
- **Библиотеки**: Lombok, MapStruct
- **Docker**: Deployment PostgreSQL
- **Миграции**: Liquibase

Перед запуском программы необходимо поднять локально Docker и в терминале выполнить команду docker-compose up.



