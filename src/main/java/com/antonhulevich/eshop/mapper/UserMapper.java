package com.antonhulevich.eshop.mapper;

import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "activated", source = "activateCode", qualifiedByName = "mapActivated")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);

    @Named("mapActivated")
    default boolean mapActivated(String activateCode) {
        return activateCode == null;
    }
}
