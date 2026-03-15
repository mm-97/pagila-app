package org.mm97.pagilab.factories;

import org.mm97.pagilab.dto.CreateFilmRequestDto;
import org.mm97.pagilab.models.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public final class FilmFactory {

    public static Film createFilm(CreateFilmRequestDto createFilmRequestDto) {

        Timestamp now = Timestamp.from(Instant.now());

        Film film = new Film();
        film.setTitle(createFilmRequestDto.title());
        film.setDescription(createFilmRequestDto.description());
        film.setReleaseYear(createFilmRequestDto.releaseYear());
        film.setRentalDuration(createFilmRequestDto.rentalDuration());
        film.setRentalRate(createFilmRequestDto.rentalRate());
        film.setLength(createFilmRequestDto.length());
        film.setReplacementCost(createFilmRequestDto.replacementCost());
        film.setRating(createFilmRequestDto.rating().getDbValue());
        film.setLastUpdate(now);

        return film;
    }

    public static List<FilmActor> createFilmActors(Film film, List<Actor> actors) {

        Timestamp now = Timestamp.from(Instant.now());

        return actors.stream()
                .map(actor -> {
                    FilmActor filmActor = new FilmActor();
                    filmActor.setId(new FilmActorId(actor.getActorId(), film.getFilmId()));
                    filmActor.setActor(actor);
                    filmActor.setFilm(film);
                    filmActor.setLastUpdate(now);
                    return filmActor;
                })
                .toList();
    }

    public static List<FilmCategory> createFilmCategories(Film film, List<Category> categories) {

        Timestamp now = Timestamp.from(Instant.now());

        return categories.stream()
                .map(category -> {
                    FilmCategory filmCategory = new FilmCategory();
                    filmCategory.setId(new FilmCategoryId(category.getCategoryId(), film.getFilmId()));
                    filmCategory.setCategory(category);
                    filmCategory.setFilm(film);
                    filmCategory.setLastUpdate(now);
                    return filmCategory;
                })
                .toList();
    }
}