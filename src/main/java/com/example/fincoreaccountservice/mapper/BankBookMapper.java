package com.example.fincoreaccountservice.mapper;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.entity.BankBookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью {@link BankBookEntity} и DTO {@link BankBookDto}.
 * Обеспечивает двунаправленное преобразование объектов банковского счёта между слоем persistence и API.
 */
@Mapper(componentModel = "spring")
public interface BankBookMapper {

    /**
     * Преобразует сущность банковского счёта в DTO.
     */
    @Mapping(target = "currency", source = "currency.name")
    BankBookDto toDto(BankBookEntity bankBookEntity);

    /**
     * Преобразует DTO банковского счёта в сущность.
     */
    @Mapping(target = "currency.name", source = "currency")
    BankBookEntity toEntity(BankBookDto bankBookDto);
}
