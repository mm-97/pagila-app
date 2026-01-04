import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {api} from "../api/client";
import type {FilmDetailDto} from "../types/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";

export default function FilmDetailPage() {
    const {id} = useParams();
    const [film, setFilm] = useState<FilmDetailDto | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        async function load() {
            try {
                setLoading(true);
                if (!id) {
                    throw new Error("Missing film id");
                }
                const result = await api.getFilmById(Number(id));
                setFilm(result);
            } catch (err) {
                setError(err instanceof Error ? err.message : "Unknown error");
            } finally {
                setLoading(false);
            }
        }

        load();
    }, [id]);

    if (loading) return <Loading/>;
    if (error) return <ErrorMessage message={error}/>;
    if (!film) return <ErrorMessage message="Film not found."/>;

    return (
        <div className="page">
            <div className="panel panel-animated">
                <div className="detail-header">
                    <div>
                        <h1>{film.title}</h1>
                        <p className="muted">{film.description || "No description available."}</p>
                    </div>
                    <span className="badge">{film.rating ?? "N/A"}</span>
                </div>

                <div className="detail-grid">
                    <div>
                        <strong>Length:</strong> {film.length ?? "N/A"} min
                    </div>
                    <div>
                        <strong>Rental rate:</strong> ${film.rentalRate}
                    </div>
                    <div>
                        <strong>Rental duration:</strong> {film.rentalDuration} days
                    </div>
                    <div>
                        <strong>Replacement cost:</strong> ${film.replacementCost}
                    </div>
                </div>
            </div>

            <div className="grid grid--2">
                <div className="panel panel-animated">
                    <h2>Actors</h2>
                    <ul className="clean-list">
                        {film.actors.map((actor) => (
                            <li key={actor.actorId}>
                                {actor.firstName} {actor.lastName}
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="panel panel-animated">
                    <h2>Categories</h2>
                    <ul className="clean-list">
                        {film.categories.map((category) => (
                            <li key={category.categoryId}>{category.name}</li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}