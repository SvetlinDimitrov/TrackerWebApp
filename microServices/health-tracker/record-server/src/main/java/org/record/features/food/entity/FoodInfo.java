package org.record.features.food.entity;

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
public class FoodInfo {

  @Column(name = "food_info")
  private String info;

  @Column(name = "food_large_info")
  private String largeInfo;

  @Column(name = "food_picture")
  private String picture;
}
