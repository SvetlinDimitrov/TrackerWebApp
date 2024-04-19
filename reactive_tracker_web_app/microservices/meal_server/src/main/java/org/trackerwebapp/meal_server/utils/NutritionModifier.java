package org.trackerwebapp.meal_server.utils;

import org.trackerwebapp.meal_server.domain.dto.NutritionView;
import org.trackerwebapp.meal_server.domain.entity.NutritionEntity;
import org.trackerwebapp.shared_interfaces.domain.enums.AllowedNutrients;
import org.trackerwebapp.shared_interfaces.domain.exception.BadRequestException;
import org.trackerwebapp.shared_interfaces.domain.utils.Validator;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;

public class NutritionModifier {

  public static Mono<NutritionEntity> validateAndUpdateName(NutritionEntity entity,
                                                            NutritionView dto) {
    return Mono.just(entity)
        .filter(u -> dto.name() != null &&
            Arrays.stream(AllowedNutrients.values())
                .anyMatch(correctValue -> correctValue.getNutrientName().equals(dto.name())))
        .flatMap(u -> {
          u.setName(dto.name());
          return Mono.just(u);
        })
        .switchIfEmpty(Mono.error(
            new BadRequestException("Invalid nutrition name: " + dto.name()))
        );
  }

  public static Mono<NutritionEntity> validateAndUpdateUnit(NutritionEntity entity,
                                                            NutritionView dto) {
    return Mono.just(entity)
        .filter(u -> dto.name() != null && dto.unit() != null &&
            Arrays.stream(AllowedNutrients.values())
                .anyMatch(correctValue -> correctValue.getNutrientName().equals(dto.name())
                    && correctValue.getNutrientUnit().equals(dto.unit()))
        )
        .flatMap(u -> {
          u.setUnit(dto.unit());
          return Mono.just(u);
        })
        .switchIfEmpty(Mono.error(
            new BadRequestException("Invalid nutrition unit: " + dto.unit() + " for name : " + dto.name()))
        );
  }

  public static Mono<NutritionEntity> validateAndUpdateAmount(NutritionEntity entity,
                                                              NutritionView dto) {
    return Mono.just(entity)
        .filter(data -> Validator.validateBigDecimal(dto.amount(), BigDecimal.ONE))
        .flatMap(u -> {
          u.setAmount(dto.amount());
          return Mono.just(u);
        })
        .switchIfEmpty(Mono.error(
            new BadRequestException("Invalid nutrition amount , must be at least 1"))
        );
  }
}
