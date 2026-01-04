package org.mm97.pagilab.controllers;

import org.mm97.pagilab.dto.FilmCategoryDto;
import org.mm97.pagilab.mappers.ApiMapper;
import org.mm97.pagilab.services.FilmCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/film-categories")
public class FilmCategoryController {

    private final FilmCategoryService filmCategoryService;

    public FilmCategoryController(FilmCategoryService filmCategoryService) {
        this.filmCategoryService = filmCategoryService;
    }

    @GetMapping("/category/{categoryId}/film/{filmId}")
    public ResponseEntity<FilmCategoryDto> getById(@PathVariable int categoryId, @PathVariable int filmId) {
        return ResponseEntity.ok(ApiMapper.toFilmCategoryDto(filmCategoryService.getById(categoryId, filmId)));
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<FilmCategoryDto>> getByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(
                filmCategoryService.getDetailedByCategoryId(categoryId).stream()
                        .map(ApiMapper::toFilmCategoryDto)
                        .toList()
        );
    }

    @GetMapping("/by-film/{filmId}")
    public ResponseEntity<List<FilmCategoryDto>> getByFilmId(@PathVariable int filmId) {
        return ResponseEntity.ok(
                filmCategoryService.getDetailedByFilmId(filmId).stream()
                        .map(ApiMapper::toFilmCategoryDto)
                        .toList()
        );
    }

    @GetMapping("/exists/category/{categoryId}/film/{filmId}")
    public ResponseEntity<Boolean> exists(@PathVariable int categoryId, @PathVariable int filmId) {
        return ResponseEntity.ok(filmCategoryService.exists(categoryId, filmId));
    }
}