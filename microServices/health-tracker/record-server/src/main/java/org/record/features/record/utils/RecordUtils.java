package org.record.features.record.utils;

import java.math.BigDecimal;
import org.example.domain.user.enums.Gender;
import org.record.features.record.entity.UserDetails;

public class RecordUtils {

  public static Double getCaloriesPerDay(UserDetails user, Double BMR) {
    return switch (user.getWorkoutState()) {
      case SEDENTARY -> BMR * 1.2;
      case LIGHTLY_ACTIVE -> BMR * 1.375;
      case MODERATELY_ACTIVE -> BMR * 1.55;
      case VERY_ACTIVE -> BMR * 1.725;
      case SUPER_ACTIVE -> BMR * 1.9;
    };
  }

  public static Double getBmr(UserDetails details) {
    BigDecimal BMR;

    if (Gender.MALE == details.getGender()) {
      BMR =
          new BigDecimal("88.362")
              .add(new BigDecimal("13.397").multiply(BigDecimal.valueOf(details.getKilograms())))
              .add(new BigDecimal("4.799").multiply(BigDecimal.valueOf(details.getHeight())))
              .subtract(new BigDecimal("5.677").add(new BigDecimal(details.getAge())));
    } else {
      BMR =
          new BigDecimal("447.593")
              .add(new BigDecimal("9.247").multiply(BigDecimal.valueOf(details.getKilograms())))
              .add(new BigDecimal("3.098").multiply(BigDecimal.valueOf(details.getHeight())))
              .subtract(new BigDecimal("4.330").add(new BigDecimal(details.getAge())));
    }
    return BMR.doubleValue();
  }
}
