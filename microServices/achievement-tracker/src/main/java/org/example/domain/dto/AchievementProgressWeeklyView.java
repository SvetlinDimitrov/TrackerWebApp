package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AchievementProgressWeeklyView {

  private YearWeek week;
  private BigDecimal bestProgress;
  private BigDecimal worstProgress;
  private BigDecimal totalProgress;
}
