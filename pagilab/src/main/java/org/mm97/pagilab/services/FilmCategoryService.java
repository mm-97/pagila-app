package org.mm97.pagilab.services;

import org.mm97.pagilab.exceptions.ResourceNotFoundException;
import org.mm97.pagilab.models.FilmCategory;
import org.mm97.pagilab.models.FilmCategoryId;
import org.mm97.pagilab.repositories.FilmCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FilmCategoryService {

    private final FilmCategoryRepository filmCategoryRepository;

    public FilmCategoryService(FilmCategoryRepository filmCategoryRepository) {
        this.filmCategoryRepository = filmCategoryRepository;
    }

    public List<FilmCategory> getFilmCategoryList() {
        return filmCategoryRepository.findAll();
    }

    public FilmCategory getById(int categoryId, int filmId) {
        validatePositiveId(categoryId, "categoryId");
        validatePositiveId(filmId, "filmId");

        FilmCategoryId id = new FilmCategoryId(categoryId, filmId);

        return filmCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FilmCategory relation not found with categoryId=" + categoryId + " and filmId=" + filmId));
    }

    public List<FilmCategory> getByCategoryId(int categoryId) {
        validatePositiveId(categoryId, "categoryId");
        return filmCategoryRepository.findByIdCategoryId(categoryId);
    }

    public List<FilmCategory> getByFilmId(int filmId) {
        validatePositiveId(filmId, "filmId");
        return filmCategoryRepository.findByIdFilmId(filmId);
    }

    public List<FilmCategory> getDetailedByCategoryId(int categoryId) {
        validatePositiveId(categoryId, "categoryId");
        return filmCategoryRepository.findDetailedByCategoryId(categoryId);
    }

    public List<FilmCategory> getDetailedByFilmId(int filmId) {
        validatePositiveId(filmId, "filmId");
        return filmCategoryRepository.findDetailedByFilmId(filmId);
    }

    public boolean exists(int categoryId, int filmId) {
        validatePositiveId(categoryId, "categoryId");
        validatePositiveId(filmId, "filmId");
        return filmCategoryRepository.existsByIdCategoryIdAndIdFilmId(categoryId, filmId);
    }

    private void validatePositiveId(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be > 0");
        }
    }
}