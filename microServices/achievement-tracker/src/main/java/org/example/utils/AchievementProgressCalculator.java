package org.example.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.example.domain.dto.AchievementProgressMonthlyView;
import org.example.domain.dto.AchievementProgressWeeklyView;
import org.example.domain.dto.AchievementProgressYearlyView;
import org.example.domain.dto.YearWeek;
import org.example.domain.entity.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementProgressCalculator {
  public List<AchievementProgressMonthlyView> getMonthlyProgress(List<Achievement> achievements) {

    if (achievements.isEmpty()) {
      return List.of();
    }

    Map<YearMonth, AchievementProgressMonthlyView> monthlyProgress = new TreeMap<>();

    LocalDate oldestAchievementDate = getOldestAchievement(achievements).getDate();
    LocalDate newestAchievementDate = getNewestAchievement(achievements).getDate();

    AchievementProgressMonthlyView newAchievement = createMonthProgress(oldestAchievementDate);

    monthlyProgress.put(newAchievement.getMonth(), newAchievement);

    while (YearMonth.from(oldestAchievementDate).compareTo(YearMonth.from(newestAchievementDate))
        != 0) {

      LocalDate newDate = oldestAchievementDate.plusMonths(1);

      newAchievement = createMonthProgress(newDate);

      monthlyProgress.put(newAchievement.getMonth(), newAchievement);

      oldestAchievementDate = newDate;
    }

    for (Achievement ach : achievements) {
      YearMonth keyToInsert = YearMonth.from(ach.getDate());
      AchievementProgressMonthlyView progress = monthlyProgress.get(keyToInsert);

      progress.setBestProgress(progress.getBestProgress().max(ach.getProgress()));
      progress.setWorstProgress(
          (progress.getWorstProgress().min(ach.getProgress())).compareTo(BigDecimal.ZERO) == 0
              ? ach.getProgress()
              : progress.getWorstProgress());
      progress.setTotalProgress(progress.getTotalProgress().add(ach.getProgress()));
    }

    return monthlyProgress.values().stream().toList();
  }

  public List<AchievementProgressYearlyView> getYearlyProgress(List<Achievement> achievements) {

    if (achievements.isEmpty()) {
      return List.of();
    }

    Map<Integer, AchievementProgressYearlyView> yearlyProgresses = new TreeMap<>();

    LocalDate oldestAchievementDate = getOldestAchievement(achievements).getDate();
    LocalDate newestAchievementDate = getNewestAchievement(achievements).getDate();

    AchievementProgressYearlyView newAchievement = createYearProgress(oldestAchievementDate);

    yearlyProgresses.put(newAchievement.getYear(), newAchievement);

    while (newestAchievementDate.getYear() != oldestAchievementDate.getYear()) {

      LocalDate newDate = oldestAchievementDate.plusYears(1);

      newAchievement = createYearProgress(newDate);

      yearlyProgresses.put(newAchievement.getYear(), newAchievement);

      oldestAchievementDate = newDate;
    }

    for (Achievement ach : achievements) {
      Integer keyToInsert = ach.getDate().getYear();
      AchievementProgressYearlyView progress = yearlyProgresses.get(keyToInsert);

      progress.setBestProgress(progress.getBestProgress().max(ach.getProgress()));
      progress.setWorstProgress(
          (progress.getWorstProgress().min(ach.getProgress())).compareTo(BigDecimal.ZERO) == 0
              ? ach.getProgress()
              : progress.getWorstProgress());
      progress.setTotalProgress(progress.getTotalProgress().add(ach.getProgress()));
    }

    return yearlyProgresses.values().stream().toList();
  }

  public List<AchievementProgressWeeklyView> getWeeklyProgress(List<Achievement> achievements) {

    if (achievements.isEmpty()) {
      return List.of();
    }

    Map<YearWeek, AchievementProgressWeeklyView> weeklyProgresses = new TreeMap<>();

    LocalDate oldestAchievementDate = getOldestAchievement(achievements).getDate();
    LocalDate newestAchievementDate = getNewestAchievement(achievements).getDate();

    AchievementProgressWeeklyView newAchievement = createWeekProgress(oldestAchievementDate);

    weeklyProgresses.put(newAchievement.getWeek(), newAchievement);

    while (new YearWeek(oldestAchievementDate).compareTo(new YearWeek(newestAchievementDate))
        != 0) {

      LocalDate newDate = oldestAchievementDate.plusWeeks(1);

      newAchievement = createWeekProgress(newDate);

      weeklyProgresses.put(newAchievement.getWeek(), newAchievement);

      oldestAchievementDate = newDate;
    }

    for (Achievement ach : achievements) {
      YearWeek keyToInsert = new YearWeek(ach.getDate());
      AchievementProgressWeeklyView progress = weeklyProgresses.get(keyToInsert);

      progress.setBestProgress(progress.getBestProgress().max(ach.getProgress()));
      progress.setWorstProgress(
          (progress.getWorstProgress().min(ach.getProgress())).compareTo(BigDecimal.ZERO) == 0
              ? ach.getProgress()
              : progress.getWorstProgress());
      progress.setTotalProgress(progress.getTotalProgress().add(ach.getProgress()));
    }

    return weeklyProgresses.values().stream().toList();
  }

  private Achievement getNewestAchievement(List<Achievement> progresses) {
    return progresses.get(progresses.size() - 1);
  }

  private Achievement getOldestAchievement(List<Achievement> progresses) {
    return progresses.get(0);
  }

  private AchievementProgressMonthlyView createMonthProgress(LocalDate date) {
    return new AchievementProgressMonthlyView(
        YearMonth.from(date), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  private AchievementProgressYearlyView createYearProgress(LocalDate date) {
    return new AchievementProgressYearlyView(
        date.getYear(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  private AchievementProgressWeeklyView createWeekProgress(LocalDate date) {
    return new AchievementProgressWeeklyView(
        new YearWeek(date), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }
}
