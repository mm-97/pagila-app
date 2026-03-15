package org.mm97.pagilab.dto;

import org.mm97.pagilab.models.MpaaRating;

import java.math.BigDecimal;

public record FilmSummaryDto(
        int filmId,
        String title,
        String description,
        String rating,
        Short length,
        BigDecimal rentalRate
) {
}