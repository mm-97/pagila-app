package org.mm97.pagilab.dto;


import org.mm97.pagilab.models.MpaaRating;

import java.math.BigDecimal;
import java.util.List;

public record CreateFilmRequestDto(
        String title,
        String description,
        Integer releaseYear,
        Short rentalDuration,
        BigDecimal rentalRate,
        Short length,
        BigDecimal replacementCost,
        MpaaRating rating,
        List<Integer> actorIds,
        List<Integer> categoryIds
) {
}