package org.record.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;
import org.record.features.food.dto.FoodSimpleView;
import org.record.features.food.entity.Food;
import org.record.features.meal.dto.MealView;
import org.record.features.meal.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class MealMapperDecoder implements MealMapper {

  private MealMapper delegate;
  private FoodMapper foodMapper;

  @Override
  public MealView toView(Meal entity) {
    MealView view = delegate.toView(entity);

    var consumedCalories = 0.0;
    List<FoodSimpleView> foods = new ArrayList<>();

    for (Food food : entity.getFoods()) {
      consumedCalories += food.getCalories().getAmount();
      foods.add(foodMapper.toSimpleView(food));
    }

    view.setFoods(foods);
    view.setConsumedCalories(consumedCalories);

    return view;
  }

  @Autowired
  public void setDelegate(@Qualifier("delegate") MealMapper delegate) {
    this.delegate = delegate;
  }

  @Autowired
  public void setCustomFoodMapper(FoodMapper foodMapper) {
    this.foodMapper = foodMapper;
  }
}
