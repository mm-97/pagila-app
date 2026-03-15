package org.mm97.pagilab.mappers;

import org.mm97.pagilab.dto.*;
import org.mm97.pagilab.models.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class ApiMapper {

    private ApiMapper() {
    }

    public static ActorDto toActorDto(Actor actor) {
        return new ActorDto(
                actor.getActorId(),
                actor.getFirstName(),
                actor.getLastName()
        );
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName()
        );
    }

    public static FilmSummaryDto toFilmSummaryDto(Film film) {
        return new FilmSummaryDto(
                film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getRating(),
                film.getLength(),
                film.getRentalRate()
        );
    }

    public static FilmDetailDto toFilmDetailDto(Film film) {
        List<ActorDto> actors = film.getFilmActors() == null
                ? List.of()
                : film.getFilmActors().stream()
                .map(FilmActor::getActor)
                .filter(Objects::nonNull)
                .map(ApiMapper::toActorDto)
                .sorted(Comparator.comparing(ActorDto::lastName).thenComparing(ActorDto::firstName))
                .toList();

        List<CategoryDto> categories = film.getFilmCategories() == null
                ? List.of()
                : film.getFilmCategories().stream()
                .map(FilmCategory::getCategory)
                .filter(Objects::nonNull)
                .map(ApiMapper::toCategoryDto)
                .sorted(Comparator.comparing(CategoryDto::name))
                .toList();

        return new FilmDetailDto(
                film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                actors,
                categories
        );
    }

    public static FilmActorDto toFilmActorDto(FilmActor filmActor) {
        return new FilmActorDto(
                filmActor.getActor().getActorId(),
                filmActor.getActor().getFirstName(),
                filmActor.getActor().getLastName(),
                filmActor.getFilm().getFilmId(),
                filmActor.getFilm().getTitle()
        );
    }

    public static FilmCategoryDto toFilmCategoryDto(FilmCategory filmCategory) {
        return new FilmCategoryDto(
                filmCategory.getCategory().getCategoryId(),
                filmCategory.getCategory().getName(),
                filmCategory.getFilm().getFilmId(),
                filmCategory.getFilm().getTitle()
        );
    }
}