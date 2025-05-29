package org.example.domain.food.mappers;

import java.util.List;
import java.util.Optional;
import org.example.domain.food.nutriox_api.FoodItem;
import org.example.domain.food.shared.ServingView;

public class ServingMapper {

  public static List<ServingView> getServings(FoodItem dto) {

    return Optional.ofNullable(dto.getMeasures())
        .map(
            list ->
                list.stream()
                    .map(
                        measure ->
                            new ServingView(
                                Optional.ofNullable(measure.getQty()).orElse(1.0),
                                Optional.ofNullable(measure.getServingWeight()).orElse(100.0),
                                Optional.ofNullable(measure.getMeasure()).orElse("g")))
                    .toList())
        .orElse(List.of());
  }

  public static ServingView getMainServing(FoodItem dto) {

    if (dto.getBrandName() == null) {
      return new ServingView(
          Optional.ofNullable(dto.getServingQty()).orElse(1.0),
          Optional.ofNullable(dto.getServingWeightGrams()).orElse(100.0),
          Optional.ofNullable(dto.getServingUnit()).orElse("g"));
    }
    return new ServingView(
        Optional.ofNullable(dto.getServingQty()).orElse(1.0),
        Optional.ofNullable(dto.getServingWeightGrams())
            .orElse(Optional.ofNullable(dto.getNfMetricQty()).orElse(100.0)),
        Optional.ofNullable(dto.getServingUnit()).orElse("g"));
  }
}
