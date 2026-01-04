package org.mm97.pagilab.repositories;

import org.mm97.pagilab.models.FilmActor;
import org.mm97.pagilab.models.FilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {

    List<FilmActor> findByIdActorId(Integer actorId);

    List<FilmActor> findByIdFilmId(Integer filmId);

    boolean existsByIdActorIdAndIdFilmId(Integer actorId, Integer filmId);

    @Query("""
            SELECT fa
            FROM FilmActor fa
            JOIN FETCH fa.actor
            JOIN FETCH fa.film
            WHERE fa.id.actorId = :actorId
            ORDER BY fa.film.title
            """)
    List<FilmActor> findDetailedByActorId(@Param("actorId") Integer actorId);

    @Query("""
            SELECT fa
            FROM FilmActor fa
            JOIN FETCH fa.actor
            JOIN FETCH fa.film
            WHERE fa.id.filmId = :filmId
            ORDER BY fa.actor.lastName, fa.actor.firstName
            """)
    List<FilmActor> findDetailedByFilmId(@Param("filmId") Integer filmId);
}