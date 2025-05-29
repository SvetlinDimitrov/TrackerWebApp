package org.example.domain.food.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AllowedNutrients {
  Carbohydrate("Carbohydrate", "g"),
  Protein("Protein", "g"),
  Fat("Fat", "g"),
  Fiber("Fiber", "g"),
  Sugar("Sugar", "g"),
  Omega6("Linoleic Acid", "g"),
  Omega3("α-Linolenic Acid", "g"),
  Cholesterol("Cholesterol", "mg"),
  Water("Water", "L"),
  Saturated_Fat("Saturated Fat", "g"),
  Trans_Fat("Trans Fat", "g"),

  VitaminA("Vitamin A", "µg"),
  VitaminD_D2_D3("Vitamin D (D2 + D3)", "µg"),
  VitaminE("Vitamin E", "mg"),
  VitaminK("Vitamin K", "µg"),
  VitaminC("Vitamin C", "mg"),
  VitaminB1_Thiamin("Vitamin B1 (Thiamin)", "mg"),
  VitaminB2_Riboflavin("Vitamin B2 (Riboflavin)", "mg"),
  VitaminB3_Niacin("Vitamin B3 (Niacin)", "mg"),
  VitaminB5_PantothenicAcid("Vitamin B5 (Pantothenic acid)", "mg"),
  VitaminB6("Vitamin B6", "mg"),
  VitaminB7_Biotin("Vitamin B7 (Biotin)", "µg"),
  VitaminB9_Folate("Vitamin B9 (Folate)", "µg"),
  VitaminB12("Vitamin B12", "µg"),
  Choline("Choline", "mg"),

  Calcium_Ca("Calcium , Ca", "mg"),
  Chromium_Cr("Chromium , Cr", "μg"),
  Phosphorus_P("Phosphorus , P", "mg"),
  Fluoride("Fluoride", "mg"),
  Chloride("Chloride", "g"),
  Magnesium_Mg("Magnesium , Mg", "mg"),
  Sodium_Na("Sodium , Na", "mg"),
  Potassium_K("Potassium , K", "mg"),
  Iron_Fe("Iron , Fe", "mg"),
  Zinc_Zn("Zinc , Zn", "mg"),
  Copper_Cu("Copper , Cu", "μg"),
  Manganese_Mn("Manganese , Mn", "mg"),
  Iodine_I("Iodine , I", "µg"),
  Selenium_Se("Selenium , Se", "µg"),
  Molybdenum_Mo("Molybdenum , Mo", "µg");

  private final String nutrientName;
  private final String nutrientUnit;

  public static boolean isValidNutrition(String name, String unit) {
    return Arrays.stream(values())
        .anyMatch(
            nutrient ->
                nutrient.getNutrientName().equals(name) && nutrient.getNutrientUnit().equals(unit));
  }
}
