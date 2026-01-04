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
public class FilmCategoryId implements java.io.Serializable {


    private int categoryId;
    private int filmId;


    @Column(name = "category_id", nullable = false)
    public int getCategoryId() {
        return this.categoryId;
    }


    @Column(name = "film_id", nullable = false)
    public int getFilmId() {
        return this.filmId;
    }


    public boolean equals(Object other) {
        if ((this == other)) return true;
        if ((other == null)) return false;
        if (!(other instanceof FilmCategoryId)) return false;
        FilmCategoryId castOther = (FilmCategoryId) other;

        return (this.getCategoryId() == castOther.getCategoryId())
                && (this.getFilmId() == castOther.getFilmId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getCategoryId();
        result = 37 * result + this.getFilmId();
        return result;
    }


}


