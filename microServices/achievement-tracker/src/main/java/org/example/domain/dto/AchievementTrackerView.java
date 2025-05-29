package org.example.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.entity.Achievement;
import org.example.domain.entity.AchievementTracker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AchievementTrackerView {
  private String id;
  private String userId;
  private String name;
  private String description;
  private BigDecimal goal;
  private String measurement;
  private LocalDate startDate;

  private List<Achievement> dailyProgress = new LinkedList<>();
  private List<AchievementProgressWeeklyView> weeklyProgress = new LinkedList<>();
  private List<AchievementProgressMonthlyView> monthlyProgress = new LinkedList<>();
  private List<AchievementProgressYearlyView> yearlyProgress = new LinkedList<>();

  public AchievementTrackerView(
      AchievementTracker achievementTracker,
      List<AchievementProgressMonthlyView> monthlyProgress,
      List<AchievementProgressYearlyView> yearlyProgress,
      List<AchievementProgressWeeklyView> weeklyProgress) {

    this.id = achievementTracker.getId();
    this.userId = achievementTracker.getUserId();
    this.name = achievementTracker.getName();
    this.description = achievementTracker.getDescription();
    this.goal = achievementTracker.getGoal();
    this.measurement = achievementTracker.getMeasurement();
    this.startDate = achievementTracker.getStartDate();

    this.dailyProgress = achievementTracker.getDailyProgress().values().stream().toList();
    this.monthlyProgress = monthlyProgress;
    this.yearlyProgress = yearlyProgress;
    this.weeklyProgress = weeklyProgress;
  }
}
