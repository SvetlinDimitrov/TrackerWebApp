package org.record.infrastructure.mappers;

import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.record.features.food.dto.FoodSimpleView;
import org.record.features.food.entity.Food;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@DecoratedWith(FoodMapperDecoder.class)
public interface FoodMapper {

  @Mapping(target = "servingPortions", ignore = true)
  Food toEntity(FoodRequest dto);

  @Mapping(target = "mainServing", ignore = true)
  @Mapping(target = "otherServing", ignore = true)
  OwnedFoodView toView(Food entity);

  FoodSimpleView toSimpleView(Food entity);
}
