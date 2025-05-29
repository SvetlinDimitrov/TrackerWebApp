package org.record.features.food.service;

import static org.record.infrastructure.exception.ExceptionMessages.FOOD_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.example.exceptions.throwable.NotFoundException;
import org.record.features.food.dto.FoodFilter;
import org.record.features.food.entity.Food;
import org.record.features.food.repository.FoodRepository;
import org.record.features.food.repository.FoodSpecification;
import org.record.features.meal.services.MealService;
import org.record.infrastructure.config.security.SecurityUtils;
import org.record.infrastructure.mappers.FoodMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

  private final MealService mealService;
  private final FoodMapper foodMapper;
  private final FoodRepository repository;

  public Page<OwnedFoodView> getAll(UUID mealId, FoodFilter filter, Pageable pageable) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    FoodSpecification specification = new FoodSpecification(mealId, user, filter);

    return repository.findAll(specification, pageable).map(foodMapper::toView);
  }

  public OwnedFoodView get(UUID foodId, UUID mealId) {
    return foodMapper.toView(findByIdMealId(foodId, mealId));
  }

  public OwnedFoodView create(UUID mealId, FoodRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();
    var meal = mealService.findByIdAndUserId(mealId, user.id());

    var food = foodMapper.toEntity(dto);
    food.setMeal(meal);
    food.setUserId(user.id());

    return foodMapper.toView(repository.save(food));
  }

  @Transactional
  public OwnedFoodView update(UUID mealId, UUID foodId, FoodRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();
    var food = findByIdMealId(foodId, mealId);

    Food entity = foodMapper.toEntity(dto);
    entity.setMeal(food.getMeal());
    entity.setUserId(user.id());

    repository.delete(food);
    return foodMapper.toView(repository.save(entity));
  }

  public void delete(UUID mealId, UUID foodId) {
    if (!repository.existsByIdAndMealId(foodId, mealId)) {
      throw new NotFoundException(FOOD_NOT_FOUND, foodId);
    }

    repository.deleteById(foodId);
  }

  public Food findByIdMealId(UUID foodId, UUID mealId) {
    return repository
        .findByIdAndMealId(foodId, mealId)
        .orElseThrow(() -> new NotFoundException(FOOD_NOT_FOUND, foodId));
  }

  public boolean isOwner(UUID mealId, UUID foodId, UUID userId) {
    return repository.existsByIdAndMealIdAndUserId(foodId, mealId, userId);
  }
}
