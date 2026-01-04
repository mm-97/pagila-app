import {useEffect, useMemo, useState} from "react";
import {api} from "../api/client";
import type {FilmSummaryDto, PageResponse} from "../types/api";
import ErrorMessage from "../components/ErrorMessage";
import FilmCard from "../components/FilmCard";
import Loading from "../components/Loading";
import Pagination from "../components/Pagination";
import SectionTitle from "../components/SectionTitle";

function useDebouncedValue<T>(value: T, delay = 400): T {
    const [debouncedValue, setDebouncedValue] = useState(value);

    useEffect(() => {
        const timeoutId = window.setTimeout(() => {
            setDebouncedValue(value);
        }, delay);

        return () => {
            window.clearTimeout(timeoutId);
        };
    }, [value, delay]);

    return debouncedValue;
}

export default function SearchPage() {
    const [inputValue, setInputValue] = useState("");
    const [page, setPage] = useState(0);
    const [data, setData] = useState<PageResponse<FilmSummaryDto> | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const trimmedInput = useMemo(() => inputValue.trim(), [inputValue]);
    const debouncedQuery = useDebouncedValue(trimmedInput, 450);

    useEffect(() => {
        setPage(0);
    }, [debouncedQuery]);

    useEffect(() => {
        if (!debouncedQuery) {
            setData(null);
            setError("");
            setLoading(false);
            return;
        }

        const controller = new AbortController();

        async function load() {
            try {
                setLoading(true);
                setError("");

                const result = await api.searchFilms(debouncedQuery, page, 12, controller.signal);
                setData(result);
            } catch (err) {
                if (err instanceof DOMException && err.name === "AbortError") {
                    return;
                }

                setError(err instanceof Error ? err.message : "Unknown error");
            } finally {
                if (!controller.signal.aborted) {
                    setLoading(false);
                }
            }
        }

        load();

        return () => {
            controller.abort();
        };
    }, [debouncedQuery, page]);

    const hasQuery = debouncedQuery.length > 0;
    const totalResults = data?.totalElements ?? 0;

    return (
        <div className="page">
            <SectionTitle
                title="Search"
                subtitle="Search films by title or description with live results."
            />

            <div className="panel search-panel">
                <div className="search-form">
                    <input
                        type="text"
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                        placeholder="Type a film title or keyword..."
                        className="search-input"
                    />

                    <button
                        type="button"
                        onClick={() => {
                            setInputValue("");
                            setPage(0);
                            setData(null);
                            setError("");
                        }}
                        disabled={!inputValue}
                    >
                        Clear
                    </button>
                </div>

                <p className="search-hint">
                    Results update automatically after a short pause while typing.
                </p>
            </div>

            {!trimmedInput && (
                <div className="panel">
                    Start typing to search films by title or description.
                </div>
            )}

            {loading && <Loading label="Searching films..."/>}

            {error && !loading && <ErrorMessage message={error}/>}

            {hasQuery && !loading && !error && (
                <>
                    <div className="panel">
                        Found {totalResults} result{totalResults === 1 ? "" : "s"} for{" "}
                        <strong>{debouncedQuery}</strong>
                    </div>

                    {data && data.content.length > 0 ? (
                        <>
                            <div className="grid grid--cards">
                                {data.content.map((film) => (
                                    <FilmCard key={film.filmId} film={film}/>
                                ))}
                            </div>

                            <Pagination
                                page={data.number}
                                totalPages={data.totalPages}
                                onPrev={() => setPage((p) => Math.max(0, p - 1))}
                                onNext={() => setPage((p) => Math.min(data.totalPages - 1, p + 1))}
                            />
                        </>
                    ) : (
                        <div className="panel">No films found.</div>
                    )}
                </>
            )}
        </div>
    );
}