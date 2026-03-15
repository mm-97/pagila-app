import {Link} from "react-router-dom";
import type {FilmSummaryDto} from "../types/api";

type Props = {
    film: FilmSummaryDto;
};

export default function FilmCard({film}: Props) {
    return (<article className="card panel-animated">
            <div className="card__content">
                <div className="card__top">
                    <h3>{film.title}</h3>
                    <span className="badge">{film.rating ?? "N/A"}</span>
                </div>

                <p className="muted">
                    {film.description || "No description available."}
                </p>

                <div className="card__meta">
                    <span>Length: {film.length ?? "N/A"} min</span>
                    <span>Rate: ${film.rentalRate}</span>
                </div>

                <Link to={`/films/${film.filmId}`} className="card__link">
                    View details
                </Link>
            </div>
        </article>);
}