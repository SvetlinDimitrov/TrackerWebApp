package org.record.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.example.domain.food.shared.ServingRequest;
import org.example.domain.food.shared.ServingView;
import org.record.features.food.dto.FoodSimpleView;
import org.record.features.food.entity.Food;
import org.record.features.food.entity.Serving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class FoodMapperDecoder implements FoodMapper {

  private FoodMapper delegate;

  @Override
  public Food toEntity(FoodRequest dto) {
    var entity = delegate.toEntity(dto);
    List<Serving> servingPortions = new ArrayList<>();

    entity.getNutrients().forEach(nutrient -> nutrient.setFood(entity));

    var mainServing = toEntity(dto.mainServing(), entity);
    mainServing.setMain(true);
    servingPortions.add(mainServing);

    Optional.ofNullable(dto.otherServing())
        .ifPresent(
            servingRequests ->
                servingRequests.forEach(
                    servingRequest -> servingPortions.add(toEntity(servingRequest, entity))));

    entity.setServingPortions(servingPortions);

    return entity;
  }

  @Override
  public OwnedFoodView toView(Food entity) {
    var foodView = delegate.toView(entity);

    var mainServingView =
        entity.getServingPortions().stream()
            .filter(Serving::isMain)
            .findFirst()
            .map(this::toView)
            .orElseThrow();

    var otherServingView =
        entity.getServingPortions().stream()
            .filter(servingPortion -> !servingPortion.isMain())
            .map(this::toView)
            .toList();

    return new OwnedFoodView(
        entity.getId(),
        foodView.name(),
        foodView.calories(),
        mainServingView,
        foodView.foodDetails(),
        otherServingView,
        foodView.nutrients());
  }

  @Override
  public FoodSimpleView toSimpleView(Food entity) {
    var foodSimpleView = delegate.toSimpleView(entity);

    var mainServingView =
        entity.getServingPortions().stream()
            .filter(Serving::isMain)
            .findFirst()
            .map(this::toView)
            .orElseThrow();

    return new FoodSimpleView(foodSimpleView.name(), foodSimpleView.calories(), mainServingView);
  }

  private ServingView toView(Serving servingPortion) {
    return new ServingView(
        servingPortion.getAmount(), servingPortion.getServingWeight(), servingPortion.getMetric());
  }

  private Serving toEntity(ServingRequest dto, Food food) {
    var serving = new Serving();
    serving.setAmount(dto.amount());
    serving.setServingWeight(dto.servingWeight());
    serving.setMetric(dto.metric());
    serving.setFood(food);
    return serving;
  }

  @Autowired
  public void setDelegate(@Qualifier("delegate") FoodMapper delegate) {
    this.delegate = delegate;
  }
}
