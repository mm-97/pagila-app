package org.mm97.pagilab.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "film_category", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmCategory implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "categoryId", column = @Column(name = "category_id", nullable = false)),
            @AttributeOverride(name = "filmId", column = @Column(name = "film_id", nullable = false))
    })
    private FilmCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"filmActors", "filmCategories"})
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"filmCategories"})
    private Category category;

    @Column(name = "last_update", nullable = false, length = 35)
    private Timestamp lastUpdate;
}