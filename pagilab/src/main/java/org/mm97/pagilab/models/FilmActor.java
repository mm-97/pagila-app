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
@Table(name = "film_actor", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmActor implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "actorId", column = @Column(name = "actor_id", nullable = false)),
            @AttributeOverride(name = "filmId", column = @Column(name = "film_id", nullable = false))
    })
    private FilmActorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"filmActors", "filmCategories"})
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"filmActors"})
    private Actor actor;

    @Column(name = "last_update", nullable = false, length = 35)
    private Timestamp lastUpdate;
}