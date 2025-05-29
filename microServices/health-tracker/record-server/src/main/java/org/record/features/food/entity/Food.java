package org.record.features.food.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.domain.shared.BaseEntity;
import org.record.features.meal.entity.Meal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private UUID userId;

  @Embedded private Calories calories;

  @Embedded private FoodInfo foodDetails;

  @ManyToOne private Meal meal;

  @OneToMany(
      mappedBy = "food",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      fetch = FetchType.EAGER)
  private List<Serving> servingPortions = new ArrayList<>();

  @OneToMany(
      mappedBy = "food",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      fetch = FetchType.EAGER)
  private List<Nutrient> nutrients = new ArrayList<>();
}
