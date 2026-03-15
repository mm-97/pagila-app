package org.mm97.pagilab.services;

import lombok.AllArgsConstructor;
import org.mm97.pagilab.dto.CreateFilmRequestDto;
import org.mm97.pagilab.exceptions.ResourceNotFoundException;
import org.mm97.pagilab.factories.FilmFactory;
import org.mm97.pagilab.models.*;
import org.mm97.pagilab.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FilmService {

    public final Timestamp NOW = Timestamp.from(Instant.now());
    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;
    private final CategoryRepository categoryRepository;
    private final FilmActorRepository filmActorRepository;
    private final FilmCategoryRepository filmCategoryRepository;

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

    @Transactional
    public Film createFilm(CreateFilmRequestDto request) {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }

        String title = requireText(request.title(), "title");

        Short rentalDuration = request.rentalDuration();
        if (rentalDuration == null || rentalDuration <= 0) {
            throw new IllegalArgumentException("rentalDuration must be > 0");
        }

        BigDecimal rentalRate = request.rentalRate();
        if (rentalRate == null || rentalRate.signum() < 0) {
            throw new IllegalArgumentException("rentalRate must be >= 0");
        }

        BigDecimal replacementCost = request.replacementCost();
        if (replacementCost == null || replacementCost.signum() < 0) {
            throw new IllegalArgumentException("replacementCost must be >= 0");
        }

        if (request.length() != null && request.length() < 0) {
            throw new IllegalArgumentException("length must be >= 0");
        }

        List<Integer> actorIds = normalizeIds(request.actorIds(), "actorIds");
        List<Integer> categoryIds = normalizeIds(request.categoryIds(), "categoryIds");

        List<Actor> actors = actorIds.isEmpty()
                ? List.of()
                : actorRepository.findAllByActorIdIn(actorIds);

        List<Category> categories = categoryIds.isEmpty()
                ? List.of()
                : categoryRepository.findAllByCategoryIdIn(categoryIds);

        validateFoundActors(actorIds, actors);
        validateFoundCategories(categoryIds, categories);

        Film film = FilmFactory.createFilm(request);

        Film savedFilm = filmRepository.save(film);

        if (!actors.isEmpty()) {
            List<FilmActor> filmActors = FilmFactory.createFilmActors(savedFilm, actors);

            filmActorRepository.saveAll(filmActors);
        }

        if (!categories.isEmpty()) {
            List<FilmCategory> filmCategories = FilmFactory.createFilmCategories(film, categories);

            filmCategoryRepository.saveAll(filmCategories);
        }

        return savedFilm;
    }


    private List<Integer> normalizeIds(List<Integer> ids, String fieldName) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        LinkedHashSet<Integer> normalized = new LinkedHashSet<>();

        for (Integer id : ids) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException(fieldName + " must contain only positive ids");
            }
            normalized.add(id);
        }

        return new ArrayList<>(normalized);
    }

    private void validateFoundActors(List<Integer> requestedIds, List<Actor> foundActors) {
        Set<Integer> foundIds = foundActors.stream()
                .map(Actor::getActorId)
                .collect(Collectors.toSet());

        List<Integer> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("Actor ids not found: " + missingIds);
        }
    }

    private void validateFoundCategories(List<Integer> requestedIds, List<Category> foundCategories) {
        Set<Integer> foundIds = foundCategories.stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toSet());

        List<Integer> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("Category ids not found: " + missingIds);
        }
    }
}