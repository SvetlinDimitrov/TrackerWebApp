package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementProgressYearlyView {
  private Integer year;
  private BigDecimal bestProgress;
  private BigDecimal worstProgress;
  private BigDecimal totalProgress;
}
