import {useEffect, useState} from "react";
import {api} from "../api/client";
import type {FilmSummaryDto, PageResponse} from "../types/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import FilmCard from "../components/FilmCard";
import Pagination from "../components/Pagination";
import SectionTitle from "../components/SectionTitle";
import {useSearchParams} from "react-router-dom";

export default function FilmsPage() {
    const [searchParams, setSearchParams] = useSearchParams();

    const currentPageParam = Number(searchParams.get("page") ?? "0");
    const currentPage = Number.isNaN(currentPageParam) || currentPageParam < 0 ? 0 : currentPageParam;


    const [data, setData] = useState<PageResponse<FilmSummaryDto> | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        async function load() {
            try {
                setLoading(true);
                const result = await api.getFilms(currentPage, 12);
                setData(result);
            } catch (err) {
                setError(err instanceof Error ? err.message : "Unknown error");
            } finally {
                setLoading(false);
            }
        }

        load();
    }, [currentPage]);

    function goToPage(nextPage: number) {
        setSearchParams({page: String(nextPage)})
    }

    if (loading) return <Loading/>;
    if (error) return <ErrorMessage message={error}/>;
    if (!data) return <ErrorMessage message="No data available."/>;

    return (<div className="page">
            <SectionTitle
                title="Films"
                subtitle="Browse the film catalog exposed by the Spring Boot API."
            />

            <div className="grid grid--cards">
                {data.content.map((film) => (<FilmCard key={film.filmId} film={film}/>))}
            </div>

            <Pagination
                page={data.number}
                totalPages={data.totalPages}
                onPrev={() => goToPage(Math.max(0, data?.number - 1))}
                onNext={() => goToPage(Math.min(data?.totalPages - 1, data?.number + 1))}
            />
        </div>);
}