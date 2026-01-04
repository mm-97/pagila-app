package org.mm97.pagilab.repositories;

import org.mm97.pagilab.models.FilmCategory;
import org.mm97.pagilab.models.FilmCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, FilmCategoryId> {

    List<FilmCategory> findByIdCategoryId(Integer categoryId);

    List<FilmCategory> findByIdFilmId(Integer filmId);

    boolean existsByIdCategoryIdAndIdFilmId(Integer categoryId, Integer filmId);

    @Query("""
            SELECT fc
            FROM FilmCategory fc
            JOIN FETCH fc.category
            JOIN FETCH fc.film
            WHERE fc.id.categoryId = :categoryId
            ORDER BY fc.film.title
            """)
    List<FilmCategory> findDetailedByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("""
            SELECT fc
            FROM FilmCategory fc
            JOIN FETCH fc.category
            JOIN FETCH fc.film
            WHERE fc.id.filmId = :filmId
            ORDER BY fc.category.name
            """)
    List<FilmCategory> findDetailedByFilmId(@Param("filmId") Integer filmId);
}