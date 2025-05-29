package org.example.domain.food.nutriox_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandedFoodShortenDto {

  @JsonProperty("food_name")
  private String foodName;

  @JsonProperty("nix_item_id")
  private String itemId;

  @JsonProperty("brand_name")
  private String brandName;
}
