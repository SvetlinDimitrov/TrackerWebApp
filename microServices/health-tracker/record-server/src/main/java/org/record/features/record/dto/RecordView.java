package org.record.features.record.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.food.shared.NutritionIntakeView;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordView {

  private String id;
  private LocalDate date;
  private Double dailyCalories;
  private Double consumedCalories;
  private List<NutritionIntakeView> vitaminIntake;
  private List<NutritionIntakeView> mineralIntakes;
  private List<NutritionIntakeView> macroIntakes;
}
