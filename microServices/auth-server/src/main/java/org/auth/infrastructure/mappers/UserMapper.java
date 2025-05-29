package org.auth.infrastructure.mappers;

import org.auth.features.user.dto.UserCreateRequest;
import org.example.domain.user.dto.UserEditRequest;
import org.example.domain.user.dto.UserView;
import org.auth.features.user.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@DecoratedWith(UserMapperDecoder.class)
public interface UserMapper {

  User toEntity(UserCreateRequest dto);

  UserView toView(User entity);

  void update(@MappingTarget User entity, UserEditRequest dto);
}
