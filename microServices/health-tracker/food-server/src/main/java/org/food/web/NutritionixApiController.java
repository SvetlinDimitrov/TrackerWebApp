package org.food.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.food.nutriox_api.ListFoodsResponse;
import org.example.domain.food.paths.NutritionixApiControllerPaths;
import org.example.domain.food.shared.FoodView;
import org.food.infrastructure.nutritionix.NutritionixApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(NutritionixApiControllerPaths.BASE)
public class NutritionixApiController {

  private final NutritionixApiService service;

  @GetMapping(NutritionixApiControllerPaths.GET_COMMON_FOOD)
  public ResponseEntity<List<FoodView>> getFoodBySearchCriteria(@RequestParam String term) {
    return ResponseEntity.ok(service.getCommonFoodBySearchTerm(term));
  }

  @GetMapping(NutritionixApiControllerPaths.GET_BRANDED_FOOD)
  public ResponseEntity<FoodView> getBrandedFoodById(@PathVariable String id) {
    return ResponseEntity.ok(service.getBrandedFoodById(id));
  }

  @GetMapping(NutritionixApiControllerPaths.GET_FOODS_BY_NAME)
  public ResponseEntity<ListFoodsResponse> getFoodsByName(@RequestParam String foodName) {
    return ResponseEntity.ok(service.getAllFoodsByFoodName(foodName));
  }
}
