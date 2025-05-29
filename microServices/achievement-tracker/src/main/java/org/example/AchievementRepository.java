package org.example;

import org.example.domain.entity.AchievementTracker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends MongoRepository<AchievementTracker, String> {
  List<AchievementTracker> findAllByUserId(String userId);

  Optional<AchievementTracker> findByIdAndUserId(String id, String userId);

  Optional<AchievementTracker> findByNameAndUserId(String name, String userId);

  Optional<AchievementTracker> findByName(String name);
}
