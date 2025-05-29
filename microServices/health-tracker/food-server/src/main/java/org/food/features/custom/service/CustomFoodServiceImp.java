package org.food.features.custom.service;

import static org.food.infrastructure.exception.ExceptionMessages.FOOD_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.example.exceptions.throwable.NotFoundException;
import org.food.features.custom.dto.CustomFilterCriteria;
import org.food.features.custom.entity.CustomFood;
import org.food.features.custom.repository.CustomFoodRepository;
import org.food.features.custom.repository.specifications.CustomFoodSpecification;
import org.food.infrastructure.config.security.SecurityUtils;
import org.food.infrastructure.mappers.FoodMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomFoodServiceImp implements CustomFoodService {

  private final CustomFoodRepository repository;
  private final FoodMapper foodMapper;

  public Page<OwnedFoodView> getAll(Pageable pageable, CustomFilterCriteria filterCriteria) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return repository
        .findAll(new CustomFoodSpecification(user, filterCriteria), pageable)
        .map(foodMapper::toView);
  }

  public OwnedFoodView getById(UUID id) {
    return foodMapper.toView(findById(id));
  }

  public OwnedFoodView create(FoodRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    var entity = foodMapper.toEntity(dto);

    entity.setUserId(user.id());

    return foodMapper.toView(repository.save(entity));
  }

  public OwnedFoodView update(UUID id, FoodRequest request) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    var entity = foodMapper.toEntity(request);
    entity.setUserId(user.id());

    repository.deleteById(id);

    return foodMapper.toView(repository.save(entity));
  }

  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException(FOOD_NOT_FOUND, id);
    }

    repository.deleteById(id);
  }

  @Transactional
  public void deleteAllByUserId(UUID userId) {
    repository.deleteAllByUserId(userId);
  }

  public boolean existsByUserIdAndFoodId(UUID id, UUID foodId) {
    return repository.existsByIdAndUserId(foodId, id);
  }

  private CustomFood findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException(FOOD_NOT_FOUND, id));
  }
}
