package org.example.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.entity.AchievementTracker;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AchievementHolderCreateDto {

  @NotEmpty @NotBlank private String name;

  @NotEmpty
  @NotBlank
  @Length(min = 3)
  private String description;

  @NotNull
  @Min(0)
  private BigDecimal goal;

  private String measurement;

  public AchievementTracker toEntity() {
    AchievementTracker achievementTracker = new AchievementTracker();
    achievementTracker.setName(name);
    achievementTracker.setDescription(description);
    achievementTracker.setGoal(goal);
    achievementTracker.setMeasurement(measurement);
    return achievementTracker;
  }
}
