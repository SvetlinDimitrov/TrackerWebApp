package org.example.domain.food.nutriox_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodItem {

  @JsonProperty("food_name")
  private String foodName;

  @JsonProperty("brand_name")
  private String brandName;

  @JsonProperty("serving_qty")
  private Double servingQty;

  @JsonProperty("serving_unit")
  private String servingUnit;

  @JsonProperty("serving_weight_grams")
  private Double servingWeightGrams;

  @JsonProperty("nf_metric_qty")
  private Double nfMetricQty;

  @JsonProperty("nf_metric_uom")
  private String nfMetricUom;

  @JsonProperty("nf_calories")
  private Double nfCalories;

  @JsonProperty("nf_total_fat")
  private Double nfTotalFat;

  @JsonProperty("nf_saturated_fat")
  private Double nfSaturatedFat;

  @JsonProperty("nf_cholesterol")
  private Double nfCholesterol;

  @JsonProperty("nf_sodium")
  private Double nfSodium;

  @JsonProperty("nf_total_carbohydrate")
  private Double nfTotalCarbohydrate;

  @JsonProperty("nf_dietary_fiber")
  private Double nfDietaryFiber;

  @JsonProperty("nf_sugars")
  private Double nfSugars;

  @JsonProperty("nf_protein")
  private Double nfProtein;

  @JsonProperty("nf_potassium")
  private Double nfPotassium;

  @JsonProperty("full_nutrients")
  private List<FullNutrient> fullNutrients;

  @JsonProperty("nix_item_id")
  private String itemId;

  private Photo photo;

  private Tags tags;

  @JsonProperty("nf_ingredient_statement")
  private String nfIngredientStatement;

  @JsonProperty("alt_measures")
  private List<AltMeasures> measures;
}
