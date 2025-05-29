package org.record.features.record.utils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import org.example.domain.food.enums.AllowedNutrients;
import org.example.domain.food.shared.NutritionIntakeView;
import org.example.domain.user.enums.Gender;

public class VitaminCreator {

  /*
  data from: https://www.ncbi.nlm.nih.gov/books/NBK56068/table/summarytables.t2/?report=objectonly
  */
  public static final Set<String> allAllowedVitamins =
      Set.of(
          AllowedNutrients.VitaminA.getNutrientName(),
          AllowedNutrients.VitaminD_D2_D3.getNutrientName(),
          AllowedNutrients.VitaminE.getNutrientName(),
          AllowedNutrients.VitaminK.getNutrientName(),
          AllowedNutrients.VitaminC.getNutrientName(),
          AllowedNutrients.VitaminB1_Thiamin.getNutrientName(),
          AllowedNutrients.VitaminB2_Riboflavin.getNutrientName(),
          AllowedNutrients.VitaminB3_Niacin.getNutrientName(),
          AllowedNutrients.VitaminB5_PantothenicAcid.getNutrientName(),
          AllowedNutrients.VitaminB6.getNutrientName(),
          AllowedNutrients.VitaminB7_Biotin.getNutrientName(),
          AllowedNutrients.VitaminB9_Folate.getNutrientName(),
          AllowedNutrients.VitaminB12.getNutrientName(),
          AllowedNutrients.Choline.getNutrientName());

  public static Map<String, NutritionIntakeView> fillVitamins(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    fillVitaminA(map, gender, age);
    fillVitaminC(map, gender, age);
    fillVitaminD(map, gender, age);
    fillVitaminE(map, gender, age);
    fillVitaminK(map, gender, age);
    fillVitaminB1(map, gender, age);
    fillVitaminB2(map, gender, age);
    fillVitaminB3(map, gender, age);
    fillVitaminB6(map, gender, age);
    fillVitaminB9(map, gender, age);
    fillVitaminB12(map, gender, age);
    fillPantothenicAcid(map, gender, age);
    fillVitaminB7(map, gender, age);
    fillCholine(map, gender, age);
    return map;
  }

  private static void fillVitaminA(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitmainA =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminA.getNutrientName())
            .measurement(AllowedNutrients.VitaminA.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(300);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(400);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(600);
          } else {
            dailyIntake = BigDecimal.valueOf(900);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(600);
          } else {
            dailyIntake = BigDecimal.valueOf(700);
          }
        }
      }
    }
    vitmainA.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitmainA.getName(), vitmainA);
  }

  private static void fillVitaminC(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitmainC =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminC.getNutrientName())
            .measurement(AllowedNutrients.VitaminC.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(15);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(25);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(45);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(75);
          } else {
            dailyIntake = BigDecimal.valueOf(90);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(45);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(65);
          } else {
            dailyIntake = BigDecimal.valueOf(75);
          }
        }
      }
    }
    vitmainC.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitmainC.getName(), vitmainC);
  }

  private static void fillVitaminD(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitaminD =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminD_D2_D3.getNutrientName())
            .measurement(AllowedNutrients.VitaminD_D2_D3.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 8, age)) {
      dailyIntake = BigDecimal.valueOf(15);
    } else {
      switch (gender) {
        case MALE, FEMALE -> {
          if (ageBetween(9, 70, age)) {
            dailyIntake = BigDecimal.valueOf(15);
          } else {
            dailyIntake = BigDecimal.valueOf(20);
          }
        }
      }
    }
    vitaminD.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitaminD.getName(), vitaminD);
  }

  private static void fillVitaminE(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitaminE =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminE.getNutrientName())
            .measurement(AllowedNutrients.VitaminE.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(6);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(7);
    } else {
      switch (gender) {
        case MALE, FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(11);
          } else {
            dailyIntake = BigDecimal.valueOf(15);
          }
        }
      }
    }
    vitaminE.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitaminE.getName(), vitaminE);
  }

  private static void fillVitaminK(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminK.getNutrientName())
            .measurement(AllowedNutrients.VitaminK.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(30);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(55);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(60);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(75);
          } else {
            dailyIntake = BigDecimal.valueOf(120);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(60);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(75);
          } else {
            dailyIntake = BigDecimal.valueOf(90);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB1(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB1_Thiamin.getNutrientName())
            .measurement(AllowedNutrients.VitaminB1_Thiamin.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(0.5);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(0.6);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(0.9);
          } else {
            dailyIntake = BigDecimal.valueOf(1.2);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(0.9);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(1);
          } else {
            dailyIntake = BigDecimal.valueOf(1.1);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB2(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB2_Riboflavin.getNutrientName())
            .measurement(AllowedNutrients.VitaminB2_Riboflavin.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(0.5);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(0.6);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(0.9);
          } else {
            dailyIntake = BigDecimal.valueOf(1.3);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(0.9);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(1);
          } else {
            dailyIntake = BigDecimal.valueOf(1.1);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB3(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB3_Niacin.getNutrientName())
            .measurement(AllowedNutrients.VitaminB3_Niacin.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(6);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(8);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(12);
          } else {
            dailyIntake = BigDecimal.valueOf(16);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(12);
          } else {
            dailyIntake = BigDecimal.valueOf(14);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB6(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB6.getNutrientName())
            .measurement(AllowedNutrients.VitaminB6.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(0.5);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(0.6);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(1);
          } else if (ageBetween(14, 50, age)) {
            dailyIntake = BigDecimal.valueOf(1.3);
          } else {
            dailyIntake = BigDecimal.valueOf(1.7);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(1);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(1.2);
          } else if (ageBetween(19, 50, age)) {
            dailyIntake = BigDecimal.valueOf(1.3);
          } else {
            dailyIntake = BigDecimal.valueOf(1.5);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB9(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB9_Folate.getNutrientName())
            .measurement(AllowedNutrients.VitaminB9_Folate.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(150);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(200);
    } else {
      switch (gender) {
        case MALE, FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(300);
          } else {
            dailyIntake = BigDecimal.valueOf(400);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB12(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB12.getNutrientName())
            .measurement(AllowedNutrients.VitaminB12.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(0.9);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(1.2);
    } else {
      switch (gender) {
        case MALE, FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(1.8);
          } else {
            dailyIntake = BigDecimal.valueOf(2.4);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillPantothenicAcid(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB5_PantothenicAcid.getNutrientName())
            .measurement(AllowedNutrients.VitaminB5_PantothenicAcid.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(2);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(3);
    } else {
      switch (gender) {
        case MALE, FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(4);
          } else {
            dailyIntake = BigDecimal.valueOf(5);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillVitaminB7(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.VitaminB7_Biotin.getNutrientName())
            .measurement(AllowedNutrients.VitaminB7_Biotin.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(8);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(12);
    } else {
      switch (gender) {
        case MALE, FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(20);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(25);
          } else {
            dailyIntake = BigDecimal.valueOf(30);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static void fillCholine(
      Map<String, NutritionIntakeView> map, Gender gender, Integer age) {
    NutritionIntakeView vitamin =
        NutritionIntakeView.builder()
            .name(AllowedNutrients.Choline.getNutrientName())
            .measurement(AllowedNutrients.Choline.getNutrientUnit())
            .dailyConsumed(BigDecimal.ZERO.doubleValue())
            .build();

    BigDecimal dailyIntake = BigDecimal.ZERO;

    if (ageBetween(1, 3, age)) {
      dailyIntake = BigDecimal.valueOf(200);
    } else if (ageBetween(4, 8, age)) {
      dailyIntake = BigDecimal.valueOf(250);
    } else {
      switch (gender) {
        case MALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(375);
          } else {
            dailyIntake = BigDecimal.valueOf(550);
          }
        }
        case FEMALE -> {
          if (ageBetween(9, 13, age)) {
            dailyIntake = BigDecimal.valueOf(375);
          } else if (ageBetween(14, 18, age)) {
            dailyIntake = BigDecimal.valueOf(400);
          } else {
            dailyIntake = BigDecimal.valueOf(425);
          }
        }
      }
    }
    vitamin.setRecommendedIntake(dailyIntake.doubleValue());
    map.put(vitamin.getName(), vitamin);
  }

  private static boolean ageBetween(Integer minAge, Integer maxAge, Integer age) {
    return (age >= minAge && age <= maxAge);
  }
}
