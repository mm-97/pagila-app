package org.mm97.pagilab.repositories;

import org.mm97.pagilab.models.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    Page<Actor> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    Page<Actor> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    Page<Actor> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    @Query("""
            SELECT a
            FROM Actor a
            WHERE lower(a.firstName) LIKE lower(concat('%', :name, '%'))
               OR lower(a.lastName) LIKE lower(concat('%', :name, '%'))
            """)
    Page<Actor> searchByName(@Param("name") String name, Pageable pageable);

    @Query("""
            SELECT a
            FROM Actor a
            JOIN a.filmActors fa
            WHERE fa.film.filmId = :filmId
            ORDER BY a.lastName, a.firstName
            """)
    List<Actor> findAllByFilmId(@Param("filmId") Integer filmId);
}