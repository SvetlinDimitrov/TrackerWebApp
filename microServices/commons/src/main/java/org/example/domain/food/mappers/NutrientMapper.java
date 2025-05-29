package org.example.domain.food.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.domain.food.enums.AllowedNutrients;
import org.example.domain.food.nutriox_api.FoodItem;
import org.example.domain.food.nutriox_api.FullNutrient;
import org.example.domain.food.shared.NutritionView;

public class NutrientMapper {

  public static List<NutritionView> getNutrients(FoodItem dto) {
    List<NutritionView> nutrients = new ArrayList<>();
    fillVitamins(nutrients, dto);
    fillMinerals(nutrients, dto);
    fillMacros(nutrients, dto);
    return nutrients.stream()
        .filter(data -> Double.compare(data.amount(), 0.0) > 0)
        .map(data -> new NutritionView(data.name(), data.unit(), data.amount()))
        .toList();
  }

  private static void fillVitamins(List<NutritionView> list, FoodItem dto) {
    fillVitaminA(list, dto);
    fillVitaminD(list, dto);
    fillVitaminC(list, dto);
    fillVitaminK(list, dto);
    fillVitaminB1(list, dto);
    fillVitaminB2(list, dto);
    fillVitaminB3(list, dto);
    fillVitaminB5(list, dto);
    fillVitaminB6(list, dto);
    fillVitaminB12(list, dto);
    fillVitaminB9(list, dto);
    fillCholine(list, dto);
    fillVitaminE(list, dto);
  }

  private static void fillMinerals(List<NutritionView> list, FoodItem dto) {
    fillCalcium(list, dto);
    fillPhosphorus(list, dto);
    fillFluoride(list, dto);
    fillMagnesium(list, dto);
    fillSodium(list, dto);
    fillIron(list, dto);
    fillZinc(list, dto);
    fillCopper(list, dto);
    fillManganese(list, dto);
    fillSelenium(list, dto);
  }

  private static void fillMacros(List<NutritionView> list, FoodItem dto) {
    fillCarbohydrate(list, dto);
    fillProtein(list, dto);
    fillFat(list, dto);
    fillFiber(list, dto);
    fillSugar(list, dto);
    fillOmega6(list, dto);
    fillOmega3(list, dto);
    fillCholesterol(list, dto);
    fillWater(list, dto);
    fillSaturatedFat(list, dto);
    fillTransFat(list, dto);
  }

  private static void fillVitaminA(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 320).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminA.getNutrientName(),
                      AllowedNutrients.VitaminA.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminD(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 328).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminD_D2_D3.getNutrientName(),
                      AllowedNutrients.VitaminD_D2_D3.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminE(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 323).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminE.getNutrientName(),
                      AllowedNutrients.VitaminE.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminK(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 430).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminK.getNutrientName(),
                      AllowedNutrients.VitaminK.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminC(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 401).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminC.getNutrientName(),
                      AllowedNutrients.VitaminC.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB1(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 404).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB1_Thiamin.getNutrientName(),
                      AllowedNutrients.VitaminB1_Thiamin.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB2(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 405).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB2_Riboflavin.getNutrientName(),
                      AllowedNutrients.VitaminB2_Riboflavin.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB3(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 406).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB3_Niacin.getNutrientName(),
                      AllowedNutrients.VitaminB3_Niacin.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB5(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 410).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB5_PantothenicAcid.getNutrientName(),
                      AllowedNutrients.VitaminB5_PantothenicAcid.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB6(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 415).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB6.getNutrientName(),
                      AllowedNutrients.VitaminB6.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB9(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 417).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB9_Folate.getNutrientName(),
                      AllowedNutrients.VitaminB9_Folate.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillVitaminB12(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 418).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.VitaminB12.getNutrientName(),
                      AllowedNutrients.VitaminB12.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  private static void fillCholine(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 421).findFirst())
        .ifPresent(
            value -> {
              NutritionView vitaminA =
                  new NutritionView(
                      AllowedNutrients.Choline.getNutrientName(),
                      AllowedNutrients.Choline.getNutrientUnit(),
                      value.getValue());
              list.add(vitaminA);
            });
  }

  // Vitamin B7 is not provided

  private static void fillCalcium(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 301).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Calcium_Ca.getNutrientName(),
                      AllowedNutrients.Calcium_Ca.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  // Chromium_Cr is not provided
  private static void fillPhosphorus(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 305).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Phosphorus_P.getNutrientName(),
                      AllowedNutrients.Phosphorus_P.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillFluoride(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 313).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Fluoride.getNutrientName(),
                      AllowedNutrients.Fluoride.getNutrientUnit(),
                      value.getValue() / 1000);
              list.add(nutrient);
            });
  }

  // Chloride is not provided
  private static void fillMagnesium(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 304).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Magnesium_Mg.getNutrientName(),
                      AllowedNutrients.Magnesium_Mg.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillSodium(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 307).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Sodium_Na.getNutrientName(),
                      AllowedNutrients.Sodium_Na.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillPotassium(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 306).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Potassium_K.getNutrientName(),
                      AllowedNutrients.Potassium_K.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillIron(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 303).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Iron_Fe.getNutrientName(),
                      AllowedNutrients.Iron_Fe.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillZinc(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 309).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Zinc_Zn.getNutrientName(),
                      AllowedNutrients.Zinc_Zn.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillCopper(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 312).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Copper_Cu.getNutrientName(),
                      AllowedNutrients.Copper_Cu.getNutrientUnit(),
                      value.getValue() * 1000.0);
              list.add(nutrient);
            });
  }

  private static void fillManganese(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 315).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Manganese_Mn.getNutrientName(),
                      AllowedNutrients.Manganese_Mn.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  // Iodine_I is not provided
  private static void fillSelenium(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 317).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Selenium_Se.getNutrientName(),
                      AllowedNutrients.Selenium_Se.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  // Molybdenum_Mo is not provided

  private static void fillCarbohydrate(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 205).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Carbohydrate.getNutrientName(),
                      AllowedNutrients.Carbohydrate.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillProtein(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 203).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Protein.getNutrientName(),
                      AllowedNutrients.Protein.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillFat(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 204).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Fat.getNutrientName(),
                      AllowedNutrients.Fat.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillFiber(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 291).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Fiber.getNutrientName(),
                      AllowedNutrients.Fiber.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillSugar(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 269).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Sugar.getNutrientName(),
                      AllowedNutrients.Sugar.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillOmega6(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .map(
            data ->
                data.stream()
                    .filter(
                        nutrient ->
                            nutrient.getTag() == 675
                                || nutrient.getTag() == 672
                                || nutrient.getTag() == 685
                                || nutrient.getTag() == 853
                                || nutrient.getTag() == 855)
                    .map(FullNutrient::getValue)
                    .reduce(0.0, Double::sum))
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Omega6.getNutrientName(),
                      AllowedNutrients.Omega6.getNutrientUnit(),
                      value);
              list.add(nutrient);
            });
  }

  private static void fillOmega3(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .map(
            data ->
                data.stream()
                    .filter(
                        nutrient ->
                            nutrient.getTag() == 851
                                || nutrient.getTag() == 852
                                || nutrient.getTag() == 629
                                || nutrient.getTag() == 631
                                || nutrient.getTag() == 621)
                    .map(FullNutrient::getValue)
                    .reduce(0.0, Double::sum))
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Omega3.getNutrientName(),
                      AllowedNutrients.Omega3.getNutrientUnit(),
                      value);
              list.add(nutrient);
            });
  }

  private static void fillCholesterol(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 601).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Cholesterol.getNutrientName(),
                      AllowedNutrients.Cholesterol.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillWater(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 601).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Water.getNutrientName(),
                      AllowedNutrients.Water.getNutrientUnit(),
                      value.getValue() / 1000.0);
              list.add(nutrient);
            });
  }

  private static void fillSaturatedFat(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 606).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Saturated_Fat.getNutrientName(),
                      AllowedNutrients.Saturated_Fat.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }

  private static void fillTransFat(List<NutritionView> list, FoodItem dto) {
    Optional.ofNullable(dto)
        .map(FoodItem::getFullNutrients)
        .flatMap(data -> data.stream().filter(nutrient -> nutrient.getTag() == 605).findFirst())
        .ifPresent(
            value -> {
              NutritionView nutrient =
                  new NutritionView(
                      AllowedNutrients.Trans_Fat.getNutrientName(),
                      AllowedNutrients.Trans_Fat.getNutrientUnit(),
                      value.getValue());
              list.add(nutrient);
            });
  }
}
/**
 * vitamin_values = { "Vitamin A": nutrient_table["VITA_RAE"], "Vitamin D (D2 + D3)":
 * nutrient_table["VITD"], "Vitamin E": nutrient_table["TOCPHA"], "Vitamin K":
 * nutrient_table["VITK1"], "Vitamin C": nutrient_table["VITC"], "Vitamin B1 (Thiamin)":
 * nutrient_table["THIA"], "Vitamin B2 (Riboflavin)": nutrient_table["RIBF"], "Vitamin B3 (Niacin)":
 * nutrient_table["NIA"], "Vitamin B5 (Pantothenic acid)": nutrient_table["PANTAC"], "Vitamin B6":
 * nutrient_table["VITB6A"], "Vitamin B7 (Biotin)": nutrient_table["VITB7"], "Vitamin B9 (Folate)":
 * nutrient_table["FOL"], "Vitamin B12": nutrient_table["VITB12"], "Choline":
 * nutrient_table["CHOLN"] }
 */
