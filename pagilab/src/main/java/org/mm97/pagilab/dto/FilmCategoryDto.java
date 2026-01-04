package org.mm97.pagilab.dto;

public record FilmCategoryDto(
        int categoryId,
        String categoryName,
        int filmId,
        String filmTitle
) {
}