package org.record.features.record.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.WorkoutState;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"record"})
@EqualsAndHashCode(exclude = {"record"})
@Entity
@Table(name = "user_details")
public class UserDetails {

  @Id
  @Column(name = "record_id", columnDefinition = "BINARY(16)")
  private UUID recordId;

  @Column(nullable = false)
  private double kilograms;

  @Column(nullable = false)
  private double height;

  @Column(nullable = false)
  private int age;

  @Column(name = "workout_state", nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutState workoutState;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @OneToOne
  @MapsId
  @JoinColumn(name = "record_id")
  private Record record;
}
