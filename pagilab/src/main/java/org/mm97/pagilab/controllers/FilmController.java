package org.mm97.pagilab.controllers;

import org.mm97.pagilab.dto.FilmDetailDto;
import org.mm97.pagilab.dto.FilmSummaryDto;
import org.mm97.pagilab.mappers.ApiMapper;
import org.mm97.pagilab.services.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<Page<FilmSummaryDto>> getAllFilms(@PageableDefault(size = 20) Pageable pageable) {
        Page<FilmSummaryDto> page = filmService.getFilmPage(pageable).map(ApiMapper::toFilmSummaryDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmSummaryDto> getFilmById(@PathVariable int id) {
        return ResponseEntity.ok(ApiMapper.toFilmSummaryDto(filmService.getFilmById(id)));
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<FilmDetailDto> getDetailedFilmById(@PathVariable int id) {
        return ResponseEntity.ok(ApiMapper.toFilmDetailDto(filmService.getDetailedFilmById(id)));
    }

    @GetMapping("/search/by-title")
    public ResponseEntity<Page<FilmSummaryDto>> getByTitle(
            @RequestParam String title,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(filmService.getByTitle(title, pageable).map(ApiMapper::toFilmSummaryDto));
    }

    @GetMapping("/search/title-like")
    public ResponseEntity<Page<FilmSummaryDto>> searchByTitle(
            @RequestParam String title,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(filmService.searchByTitle(title, pageable).map(ApiMapper::toFilmSummaryDto));
    }

    @GetMapping("/search/by-rating")
    public ResponseEntity<Page<FilmSummaryDto>> getByRating(
            @RequestParam String rating,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(filmService.getByRating(rating, pageable).map(ApiMapper::toFilmSummaryDto));
    }

    @GetMapping("/search/by-min-length")
    public ResponseEntity<Page<FilmSummaryDto>> getLongerThan(
            @RequestParam short length,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(filmService.getLongerThan(length, pageable).map(ApiMapper::toFilmSummaryDto));
    }

    @GetMapping("/search/by-min-rental-rate")
    public ResponseEntity<Page<FilmSummaryDto>> getByMinimumRentalRate(
            @RequestParam BigDecimal rentalRate,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                filmService.getByMinimumRentalRate(rentalRate, pageable).map(ApiMapper::toFilmSummaryDto)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FilmSummaryDto>> searchByTitleOrDescription(
            @RequestParam String q,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                filmService.searchByTitleOrDescription(q, pageable).map(ApiMapper::toFilmSummaryDto)
        );
    }

    @GetMapping("/by-actor/{actorId}")
    public ResponseEntity<List<FilmSummaryDto>> getFilmsByActorId(@PathVariable int actorId) {
        return ResponseEntity.ok(
                filmService.getFilmsByActorId(actorId).stream().map(ApiMapper::toFilmSummaryDto).toList()
        );
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<FilmSummaryDto>> getFilmsByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(
                filmService.getFilmsByCategoryId(categoryId).stream().map(ApiMapper::toFilmSummaryDto).toList()
        );
    }
}