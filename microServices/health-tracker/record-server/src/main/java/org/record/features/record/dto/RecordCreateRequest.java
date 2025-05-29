package org.record.features.record.dto;

import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record RecordCreateRequest(
    @PastOrPresent(message = "The date cannot be in the future.") LocalDate date) {}
