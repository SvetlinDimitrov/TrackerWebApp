package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

@Getter
@Setter
@EqualsAndHashCode
public class YearWeek implements Comparable<YearWeek> {
  private final int year;
  private final int week;

  public YearWeek(LocalDate date) {
    if (date.getDayOfWeek().getValue() > 4) {
      date = date.with(TemporalAdjusters.next(java.time.DayOfWeek.MONDAY));
    }
    year = date.getYear();
    week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
  }

  @Override
  public int compareTo(YearWeek other) {
    int yearComparison = Integer.compare(this.year, other.year);
    if (yearComparison != 0) {
      return yearComparison;
    } else {
      return Integer.compare(this.week, other.week);
    }
  }

  @Override
  public String toString() {
    return "%d-%02d".formatted(year, week);
  }

  @JsonValue
  public String toJson() {
    return toString();
  }
}
