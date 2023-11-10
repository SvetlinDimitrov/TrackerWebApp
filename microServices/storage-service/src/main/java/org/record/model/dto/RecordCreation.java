package org.record.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.record.model.enums.Gender;
import org.record.model.enums.WorkoutState;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordCreation {

    private Long recordId;
    private Gender gender;
    private BigDecimal caloriesPerDay;
    private WorkoutState workoutState;

}
