package org.food.domain.entity.storageEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nutrient {
    private String name;
    private String unit;
    private BigDecimal amount;
}
