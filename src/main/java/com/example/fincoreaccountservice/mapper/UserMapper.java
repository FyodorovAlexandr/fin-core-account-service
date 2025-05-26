package com.example.fincoreaccountservice.mapper;

import com.example.fincoreaccountservice.dto.UserDto;
import com.example.fincoreaccountservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Маппер для преобразования между сущностью {@link UserEntity} и DTO {@link UserDto}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address.country", source = "address.country")
    @Mapping(target = "address.city", source = "address.city")
    @Mapping(target = "address.street", source = "address.street")
    @Mapping(target = "address.home", source = "address.home")
    UserDto toDto(UserEntity entity);

    @Mapping(target = "address.country", source = "address.country")
    @Mapping(target = "address.city", source = "address.city")
    @Mapping(target = "address.street", source = "address.street")
    @Mapping(target = "address.home", source = "address.home")
    UserEntity toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.country", source = "address.country")
    @Mapping(target = "address.city", source = "address.city")
    @Mapping(target = "address.street", source = "address.street")
    @Mapping(target = "address.home", source = "address.home")
    void updateEntityFromDto(UserDto dto, @MappingTarget UserEntity entity);
}