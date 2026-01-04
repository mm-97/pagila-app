package org.mm97.pagilab.services;

import org.mm97.pagilab.exceptions.ResourceNotFoundException;
import org.mm97.pagilab.models.Actor;
import org.mm97.pagilab.repositories.ActorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Page<Actor> getActorPage(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    public Actor getActorById(int id) {
        validatePositiveId(id, "actorId");
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found with id: " + id));
    }

    public Page<Actor> getByFirstName(String firstName, Pageable pageable) {
        return actorRepository.findByFirstNameContainingIgnoreCase(requireText(firstName, "firstName"), pageable);
    }

    public Page<Actor> getByLastName(String lastName, Pageable pageable) {
        return actorRepository.findByLastNameContainingIgnoreCase(requireText(lastName, "lastName"), pageable);
    }

    public Page<Actor> getByFirstNameAndLastName(String firstName, String lastName, Pageable pageable) {
        return actorRepository.findByFirstNameAndLastName(
                requireText(firstName, "firstName"),
                requireText(lastName, "lastName"),
                pageable
        );
    }

    public Page<Actor> searchByName(String name, Pageable pageable) {
        return actorRepository.searchByName(requireText(name, "name"), pageable);
    }

    public List<Actor> getActorsByFilmId(int filmId) {
        validatePositiveId(filmId, "filmId");
        return actorRepository.findAllByFilmId(filmId);
    }

    private void validatePositiveId(int value, String fieldName) {
        if (value <= 0) throw new IllegalArgumentException(fieldName + " must be > 0");
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(fieldName + " must not be blank");
        return value.trim();
    }
}