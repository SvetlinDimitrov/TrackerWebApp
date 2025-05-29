package org.food.infrastructure.nutritionix;

import static org.food.infrastructure.exception.ExceptionMessages.NUTRITIONIX_NOT_FOUND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.domain.food.nutriox_api.ListFoodsResponse;
import org.example.domain.food.shared.FoodView;
import org.example.exceptions.throwable.BadRequestException;
import org.food.infrastructure.config.openfeign.NutritionixClient;
import org.food.infrastructure.mappers.FoodMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutritionixApiService {

  private final NutritionixClient nutritionixClient;
  private final FoodMapper foodMapper;

  public List<FoodView> getCommonFoodBySearchTerm(String query) {
    if (query.isBlank()) {
      throw new BadRequestException(NUTRITIONIX_NOT_FOUND);
    }

    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("query", query);

    return nutritionixClient.getCommonFood(requestBody).getFoods().stream()
        .map(foodMapper::toView)
        .collect(Collectors.toList());
  }

  public FoodView getBrandedFoodById(String id) {
    if (id.isBlank()) {
      throw new BadRequestException(NUTRITIONIX_NOT_FOUND);
    }

    return nutritionixClient.getBrandedFood(id).getFoods().stream()
        .map(foodMapper::toView)
        .findFirst()
        .orElseThrow(() -> new BadRequestException(NUTRITIONIX_NOT_FOUND));
  }

  public ListFoodsResponse getAllFoodsByFoodName(String foodName) {
    if (foodName.isBlank()) {
      throw new BadRequestException(NUTRITIONIX_NOT_FOUND);
    }

    return nutritionixClient.getAllFoods(foodName);
  }
}
