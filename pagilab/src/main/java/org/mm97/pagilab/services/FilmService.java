package org.mm97.pagilab.services;

import org.mm97.pagilab.exceptions.ResourceNotFoundException;
import org.mm97.pagilab.models.Film;
import org.mm97.pagilab.repositories.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Page<Film> getFilmPage(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    public Film getFilmById(int id) {
        validatePositiveId(id, "filmId");
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + id));
    }

    public Film getDetailedFilmById(int id) {
        validatePositiveId(id, "filmId");
        Film film = filmRepository.findDetailedById(id);
        if (film == null) throw new ResourceNotFoundException("Film not found with id: " + id);
        return film;
    }

    public Page<Film> getByTitle(String title, Pageable pageable) {
        return filmRepository.findByTitleContainingIgnoreCase(requireText(title, "title"), pageable);
    }

    public Page<Film> searchByTitle(String title, Pageable pageable) {
        return filmRepository.findByTitleContainingIgnoreCase(requireText(title, "title"), pageable);
    }

    public Page<Film> getByRating(String rating, Pageable pageable) {
        return filmRepository.findByRatingIgnoreCase(requireText(rating, "rating"), pageable);
    }

    public Page<Film> getLongerThan(short length, Pageable pageable) {
        if (length < 0) throw new IllegalArgumentException("length must be >= 0");
        return filmRepository.findByLengthGreaterThan(length, pageable);
    }

    public Page<Film> getByMinimumRentalRate(BigDecimal rentalRate, Pageable pageable) {
        if (rentalRate == null || rentalRate.signum() < 0) {
            throw new IllegalArgumentException("rentalRate must be >= 0");
        }
        return filmRepository.findByRentalRateGreaterThanEqual(rentalRate, pageable);
    }

    public Page<Film> searchByTitleOrDescription(String text, Pageable pageable) {
        return filmRepository.searchByTitleOrDescription(requireText(text, "text"), pageable);
    }

    public List<Film> getFilmsByActorId(int actorId) {
        validatePositiveId(actorId, "actorId");
        return filmRepository.findAllByActorId(actorId);
    }

    public List<Film> getFilmsByCategoryId(int categoryId) {
        validatePositiveId(categoryId, "categoryId");
        return filmRepository.findAllByCategoryId(categoryId);
    }

    private void validatePositiveId(int value, String fieldName) {
        if (value <= 0) throw new IllegalArgumentException(fieldName + " must be > 0");
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(fieldName + " must not be blank");
        return value.trim();
    }
}