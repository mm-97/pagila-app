package org.mm97.pagilab.controllers;

import org.mm97.pagilab.dto.FilmActorDto;
import org.mm97.pagilab.mappers.ApiMapper;
import org.mm97.pagilab.services.FilmActorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/film-actors")
public class FilmActorController {

    private final FilmActorService filmActorService;

    public FilmActorController(FilmActorService filmActorService) {
        this.filmActorService = filmActorService;
    }

    @GetMapping("/actor/{actorId}/film/{filmId}")
    public ResponseEntity<FilmActorDto> getById(@PathVariable int actorId, @PathVariable int filmId) {
        return ResponseEntity.ok(ApiMapper.toFilmActorDto(filmActorService.getById(actorId, filmId)));
    }

    @GetMapping("/by-actor/{actorId}")
    public ResponseEntity<List<FilmActorDto>> getByActorId(@PathVariable int actorId) {
        return ResponseEntity.ok(
                filmActorService.getDetailedByActorId(actorId).stream().map(ApiMapper::toFilmActorDto).toList()
        );
    }

    @GetMapping("/by-film/{filmId}")
    public ResponseEntity<List<FilmActorDto>> getByFilmId(@PathVariable int filmId) {
        return ResponseEntity.ok(
                filmActorService.getDetailedByFilmId(filmId).stream().map(ApiMapper::toFilmActorDto).toList()
        );
    }

    @GetMapping("/exists/actor/{actorId}/film/{filmId}")
    public ResponseEntity<Boolean> exists(@PathVariable int actorId, @PathVariable int filmId) {
        return ResponseEntity.ok(filmActorService.exists(actorId, filmId));
    }
}