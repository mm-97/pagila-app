package org.mm97.pagilab.models;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmActorId implements java.io.Serializable {


    private int actorId;
    private int filmId;


    @Column(name = "actor_id", nullable = false)
    public int getActorId() {
        return this.actorId;
    }


    @Column(name = "film_id", nullable = false)
    public int getFilmId() {
        return this.filmId;
    }


    public boolean equals(Object other) {
        if ((this == other)) return true;
        if ((other == null)) return false;
        if (!(other instanceof FilmActorId)) return false;
        FilmActorId castOther = (FilmActorId) other;

        return (this.getActorId() == castOther.getActorId())
                && (this.getFilmId() == castOther.getFilmId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getActorId();
        result = 37 * result + this.getFilmId();
        return result;
    }


}


