package org.mm97.pagilab.dto;


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
        String rating,
        List<Integer> actorIds,
        List<Integer> categoryIds
) {
}