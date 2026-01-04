package org.mm97.pagilab.services;

import org.mm97.pagilab.exceptions.ResourceNotFoundException;
import org.mm97.pagilab.models.FilmActor;
import org.mm97.pagilab.models.FilmActorId;
import org.mm97.pagilab.repositories.FilmActorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FilmActorService {

    private final FilmActorRepository filmActorRepository;

    public FilmActorService(FilmActorRepository filmActorRepository) {
        this.filmActorRepository = filmActorRepository;
    }

    public List<FilmActor> getFilmActorList() {
        return filmActorRepository.findAll();
    }

    public FilmActor getById(int actorId, int filmId) {
        validatePositiveId(actorId, "actorId");
        validatePositiveId(filmId, "filmId");

        FilmActorId id = new FilmActorId(actorId, filmId);

        return filmActorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FilmActor relation not found with actorId=" + actorId + " and filmId=" + filmId));
    }

    public List<FilmActor> getByActorId(int actorId) {
        validatePositiveId(actorId, "actorId");
        return filmActorRepository.findByIdActorId(actorId);
    }

    public List<FilmActor> getByFilmId(int filmId) {
        validatePositiveId(filmId, "filmId");
        return filmActorRepository.findByIdFilmId(filmId);
    }

    public List<FilmActor> getDetailedByActorId(int actorId) {
        validatePositiveId(actorId, "actorId");
        return filmActorRepository.findDetailedByActorId(actorId);
    }

    public List<FilmActor> getDetailedByFilmId(int filmId) {
        validatePositiveId(filmId, "filmId");
        return filmActorRepository.findDetailedByFilmId(filmId);
    }

    public boolean exists(int actorId, int filmId) {
        validatePositiveId(actorId, "actorId");
        validatePositiveId(filmId, "filmId");
        return filmActorRepository.existsByIdActorIdAndIdFilmId(actorId, filmId);
    }

    private void validatePositiveId(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be > 0");
        }
    }
}