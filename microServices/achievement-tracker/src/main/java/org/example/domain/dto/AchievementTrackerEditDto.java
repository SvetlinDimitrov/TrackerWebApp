package org.example.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AchievementTrackerEditDto {

  private String description;
  private String name;
  private BigDecimal goal;
  private String measurement;
}
