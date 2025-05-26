package com.example.fincoreaccountservice.mapper;

import com.example.fincoreaccountservice.dto.BankBookDto;
import com.example.fincoreaccountservice.entity.BankBookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью {@link BankBookEntity} и DTO {@link BankBookDto}.
 */
@Mapper(componentModel = "spring")
public interface BankBookMapper {

    @Mapping(target = "currency", source = "currency.name")
    BankBookDto toDto(BankBookEntity bankBookEntity);

    @Mapping(target = "currency.name", source = "currency")
    BankBookEntity toEntity(BankBookDto bankBookDto);
}
