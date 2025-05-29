package org.record.features.food.repository;

import java.util.Optional;
import java.util.UUID;
import org.record.features.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID>, JpaSpecificationExecutor<Food> {

  @Query("SELECT f FROM Food f WHERE f.id = :foodId " + "AND f.meal.id = :mealId")
  Optional<Food> findByIdAndMealId(@Param("foodId") UUID foodId, @Param("mealId") UUID mealId);

  @Query(
      "SELECT CASE WHEN COUNT(f) > 0 "
          + "THEN TRUE ELSE FALSE END "
          + "FROM Food f WHERE f.id = :foodId "
          + "AND f.meal.id = :mealId "
          + "AND f.userId = :userId")
  boolean existsByIdAndMealIdAndUserId(
      @Param("foodId") UUID foodId, @Param("mealId") UUID mealId, @Param("userId") UUID userId);

  boolean existsByIdAndMealId(UUID foodId, UUID mealId);
}
