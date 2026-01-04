package org.mm97.pagilab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "film", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film implements java.io.Serializable {

    @Id
    @Column(name = "film_id", unique = true, nullable = false)
    private int filmId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "rental_duration", nullable = false)
    private short rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost;

    @Column(name = "rating")
    private String rating;

    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;

    @Transient
    private Object specialFeatures;

    @Transient
    private Object fulltext;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "film")
    private Set<FilmCategory> filmCategories = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "film")
    private Set<FilmActor> filmActors = new HashSet<>();
}