import {useEffect, useState} from "react";
import {api} from "../api/client";
import type {ActorDto, PageResponse} from "../types/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import Pagination from "../components/Pagination";
import SectionTitle from "../components/SectionTitle";

export default function ActorsPage() {
    const [data, setData] = useState<PageResponse<ActorDto> | null>(null);
    const [page, setPage] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const pagesize = 20;

    useEffect(() => {
        async function load() {
            try {
                setLoading(true);
                const result = await api.getActors(page, pagesize);
                setData(result);
            } catch (err) {
                setError(err instanceof Error ? err.message : "Unknown error");
            } finally {
                setLoading(false);
            }
        }

        load();
    }, [page]);

    if (loading) return <Loading/>;
    if (error) return <ErrorMessage message={error}/>;
    if (!data) return <ErrorMessage message="No data available."/>;

    return (<div className="page">
            <SectionTitle
                title="Actors"
                subtitle="Paginated list of actors exposed by the Spring Boot API, with avatar images dynamically generated using the DiceBear API."
            />
            <div className="list-panel">
                {data.content.map((actor: ActorDto, index: number) => (
                    <div key={actor.actorId} className="list-row list-row--actor panel-animated">
                        <span>{page * pagesize + index + 1}</span>
                        <img
                            src={`https://api.dicebear.com/7.x/adventurer/svg?seed=${actor.actorId}`}
                            alt={`${actor.firstName} ${actor.lastName}`}
                            className="actor-avatar"
                        />
                        <span>{actor.firstName} {actor.lastName}</span>
                    </div>))}
            </div>

            <Pagination
                page={data.number}
                totalPages={data.totalPages}
                onPrev={() => setPage((p) => Math.max(0, p - 1))}
                onNext={() => setPage((p) => Math.min(data.totalPages - 1, p + 1))}
            />
        </div>);
}