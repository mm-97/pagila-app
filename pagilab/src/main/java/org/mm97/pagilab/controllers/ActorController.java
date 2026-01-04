package org.mm97.pagilab.controllers;

import org.mm97.pagilab.dto.ActorDto;
import org.mm97.pagilab.mappers.ApiMapper;
import org.mm97.pagilab.services.ActorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<Page<ActorDto>> getAllActors(@PageableDefault(size = 20) Pageable pageable) {
        Page<ActorDto> page = actorService.getActorPage(pageable).map(ApiMapper::toActorDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable int id) {
        return ResponseEntity.ok(ApiMapper.toActorDto(actorService.getActorById(id)));
    }

    @GetMapping("/search/by-first-name")
    public ResponseEntity<Page<ActorDto>> getByFirstName(
            @RequestParam String firstName,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(actorService.getByFirstName(firstName, pageable).map(ApiMapper::toActorDto));
    }

    @GetMapping("/search/by-last-name")
    public ResponseEntity<Page<ActorDto>> getByLastName(
            @RequestParam String lastName,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(actorService.getByLastName(lastName, pageable).map(ApiMapper::toActorDto));
    }

    @GetMapping("/search/by-full-name")
    public ResponseEntity<Page<ActorDto>> getByFirstNameAndLastName(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                actorService.getByFirstNameAndLastName(firstName, lastName, pageable).map(ApiMapper::toActorDto)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ActorDto>> searchByName(
            @RequestParam String q,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(actorService.searchByName(q, pageable).map(ApiMapper::toActorDto));
    }

    @GetMapping("/by-film/{filmId}")
    public ResponseEntity<java.util.List<ActorDto>> getActorsByFilmId(@PathVariable int filmId) {
        return ResponseEntity.ok(
                actorService.getActorsByFilmId(filmId).stream().map(ApiMapper::toActorDto).toList()
        );
    }
}