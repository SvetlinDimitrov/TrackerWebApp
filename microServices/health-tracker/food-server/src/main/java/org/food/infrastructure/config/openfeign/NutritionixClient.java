package org.food.infrastructure.config.openfeign;

import org.example.domain.food.nutriox_api.GetFoodsResponse;
import org.example.domain.food.nutriox_api.ListFoodsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
    name = "nutritionixClient",
    url = "${api.url}",
    configuration = NutritionixFeignConfig.class)
public interface NutritionixClient {

  @PostMapping(value = "/v2/natural/nutrients", consumes = MediaType.APPLICATION_JSON_VALUE)
  GetFoodsResponse getCommonFood(@RequestBody Map<String, String> requestBody);

  @GetMapping(value = "/v2/search/item", produces = MediaType.APPLICATION_JSON_VALUE)
  GetFoodsResponse getBrandedFood(@RequestParam("nix_item_id") String id);

  @GetMapping(value = "/v2/search/instant/", produces = MediaType.APPLICATION_JSON_VALUE)
  ListFoodsResponse getAllFoods(@RequestParam("query") String foodName);
}
