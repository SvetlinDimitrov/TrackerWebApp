package org.trackerwebapp.customFood_server.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public record InsertFoodDto(
    String name,
    CalorieView calories,
    String measurement,
    BigDecimal size,
    List<NutritionView> nutrients
) {

}
