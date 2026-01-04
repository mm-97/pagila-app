package org.mm97.pagilab.repositories;

import org.mm97.pagilab.models.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Film> findByRatingIgnoreCase(String rating, Pageable pageable);

    Page<Film> findByLengthGreaterThan(short length, Pageable pageable);

    Page<Film> findByRentalRateGreaterThanEqual(BigDecimal rentalRate, Pageable pageable);

    @Query("""
            SELECT f
            FROM Film f
            WHERE lower(f.title) LIKE lower(concat('%', :text, '%'))
               OR lower(f.description) LIKE lower(concat('%', :text, '%'))
            """)
    Page<Film> searchByTitleOrDescription(@Param("text") String text, Pageable pageable);

    @Query("""
            SELECT f
            FROM Film f
            JOIN f.filmActors fa
            WHERE fa.actor.actorId = :actorId
            ORDER BY f.title
            """)
    List<Film> findAllByActorId(@Param("actorId") Integer actorId);

    @Query("""
            SELECT f
            FROM Film f
            JOIN f.filmCategories fc
            WHERE fc.category.categoryId = :categoryId
            ORDER BY f.title
            """)
    List<Film> findAllByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("""
            SELECT DISTINCT f
            FROM Film f
            LEFT JOIN FETCH f.filmActors fa
            LEFT JOIN FETCH fa.actor
            LEFT JOIN FETCH f.filmCategories fc
            LEFT JOIN FETCH fc.category
            WHERE f.filmId = :filmId
            """)
    Film findDetailedById(@Param("filmId") Integer filmId);
}