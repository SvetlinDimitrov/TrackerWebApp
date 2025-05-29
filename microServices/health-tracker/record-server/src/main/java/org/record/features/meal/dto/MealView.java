package org.record.features.meal.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.record.features.food.dto.FoodSimpleView;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealView {

  private String id;
  private String name;
  private Double consumedCalories;
  private List<FoodSimpleView> foods;
}
