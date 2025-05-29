package org.record.features.meal.repository;

import feign.Param;
import java.util.Optional;
import java.util.UUID;
import org.record.features.meal.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID>, JpaSpecificationExecutor<Meal> {

  @Query("SELECT m FROM Meal m WHERE m.id = :mealId AND m.record.userId = :userId")
  Optional<Meal> findByIdAndUserId(@Param("mealId") UUID mealId, @Param("userId") UUID userId);

  @Query("SELECT COUNT(m) > 0 FROM Meal m WHERE m.id = :mealId AND m.record.userId = :userId")
  boolean existsByIdAndUserId(@Param("mealId") UUID mealId, @Param("userId") UUID userId);
}
