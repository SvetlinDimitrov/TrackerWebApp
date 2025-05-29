package org.example.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TreeMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "achievements")
public class AchievementTracker {
  @Id private String id;
  private String userId;
  private String name;
  private String description;
  private BigDecimal goal;
  private String measurement;
  private LocalDate startDate;
  private TreeMap<LocalDate, Achievement> dailyProgress = new TreeMap<>();
}
