package org.trackerwebapp.trackerwebapp.utils.meals;

import org.trackerwebapp.trackerwebapp.domain.dto.BadRequestException;
import org.trackerwebapp.trackerwebapp.domain.dto.meal.CalorieView;
import org.trackerwebapp.trackerwebapp.domain.entity.CalorieEntity;
import org.trackerwebapp.trackerwebapp.domain.enums.AllowedCalorieUnits;
import org.trackerwebapp.trackerwebapp.utils.Validator;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CalorieModifier {

  public static Mono<CalorieEntity> validateAndUpdateEntity(CalorieEntity entity, CalorieView dto) {
    return Mono.just(entity)
        .flatMap(data -> validateAndUpdateSize(data, dto))
        .flatMap(data -> validateAndUpdateUnit(data, dto));
  }

  private static Mono<CalorieEntity> validateAndUpdateUnit(CalorieEntity entity, CalorieView dto) {
    return Mono.just(entity)
        .filter(
            u -> Validator.validateString(dto.unit(), 2) &&
                AllowedCalorieUnits.CALORIE.getSymbol().equals(dto.unit()))
        .flatMap(u -> {
          u.setUnit(dto.unit());
          return Mono.just(u);
        })
        .switchIfEmpty(Mono.error(
            new BadRequestException("Invalid calorie unit"))
        );
  }

  private static Mono<CalorieEntity> validateAndUpdateSize(CalorieEntity entity, CalorieView dto) {
    return Mono.just(entity)
        .filter(u -> Validator.validateBigDecimal(dto.amount(), BigDecimal.ONE))
        .flatMap(u -> {
          u.setAmount(dto.amount());
          return Mono.just(u);
        })
        .switchIfEmpty(Mono.error(
            new BadRequestException("Invalid calorie amount , must be greater then 0"))
        );
  }
}
