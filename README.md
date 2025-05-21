# Spring Bean Lifecycle & Caching Using Custom Proxy

Проект демонстрирует работу жизненного цикла бинов в Spring. Механизмы кэширования через прокси с помощью кастомного
создания ProxyFactory и MethodInterceptor. А также механизмы кастомной обработки бинов.

## Основная функциональность

- Полный жизненный цикл бинов Spring (от создания до уничтожения)
- Кастомная аннотация `@CacheResult` для кэширования результатов методов при помощи создание прокси через ProxyFactory и
  MethodInterceptor
- Реализация `BeanPostProcessor` и `BeanFactoryPostProcessor`
- Работа с `@PostConstruct` и `@PreDestroy`
- Сравнение singleton и prototype бинов
- Динамическое проксирование методов
- Двухуровневое потокобезопасное кэширование

## После запуска в логах будет отображен полный цикл работы:

### 1. Запуск приложения

- Инициализация Spring контекста

### 2. Фаза BeanFactoryPostProcessor

- Анализ бинов перед их созданием
- Выявление prototype-бина testCacheService с аннотацией @CacheResult
- Предупреждение о потенциальных проблемах кэширования для prototype-бинов

### 3. Создание экземпляра через конструктор

### 4. Фаза BeanPostProcessor

- Обработка бина до инициализации в BeanPostProcessor.postProcessBeforeInitialization
- Инициализация бина. Вызов метода с аннотацией @PostConstruct
- Обработка бина после инициализации в BeanPostProcessor.postProcessAfterInitialization. Создание прокси для методов с
  @CacheResult. Добавление CacheResultMethodInterceptor для кэширования

### 5. Запуск основного workflow

- Тестирование кэширования
- Обработка данных
- Работа prototype-бинов

### 6. Завершение работы

- Вызов методов с аннотацией @PreDestroy
- Уничтожение бинов с очисткой ресурсов

## Технологический стек

- **Язык**: Java 21
- **Фреймворк**: Spring Boot 3.x
- **Сборка**: Maven
- **Spring Core**: DI, Beans, Context, Proxy
- **Библиотеки**: Lombok