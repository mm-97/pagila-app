package org.mm97.pagilab.dto;

import java.math.BigDecimal;
import java.util.List;

public record FilmDetailDto(
        int filmId,
        String title,
        String description,
        Object releaseYear,
        short rentalDuration,
        BigDecimal rentalRate,
        Short length,
        BigDecimal replacementCost,
        String rating,
        List<ActorDto> actors,
        List<CategoryDto> categories
) {
}