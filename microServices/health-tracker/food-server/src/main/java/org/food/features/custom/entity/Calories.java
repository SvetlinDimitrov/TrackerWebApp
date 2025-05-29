package org.food.features.custom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Embeddable
public class Calories {

  @Column(nullable = false, name = "calorie_amount")
  private Double amount;

  @Column(nullable = false, name = "calorie_unit")
  private String unit;
}
