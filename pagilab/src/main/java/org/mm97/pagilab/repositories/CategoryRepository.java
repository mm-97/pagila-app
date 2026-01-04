package org.mm97.pagilab.repositories;

import org.mm97.pagilab.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
            SELECT c
            FROM Category c
            JOIN c.filmCategories fc
            WHERE fc.film.filmId = :filmId
            ORDER BY c.name
            """)
    List<Category> findAllByFilmId(@Param("filmId") Integer filmId);
}