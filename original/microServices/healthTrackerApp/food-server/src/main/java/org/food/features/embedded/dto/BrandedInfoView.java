package org.food.features.embedded.dto;

import java.math.BigDecimal;

public record BrandedInfoView(
    String ingredients,
    String brandOwner,
    String marketCountry,
    BigDecimal servingSize,
    String servingSizeUnit
) {
}