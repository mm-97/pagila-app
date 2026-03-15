import {useEffect, useMemo, useState} from "react";
import {api} from "../api/client.ts";
import type {FilmSummaryDto, PageResponse} from "../types/api.ts";
import ErrorMessage from "../components/ErrorMessage.tsx";
import FilmCard from "../components/FilmCard.tsx";
import Loading from "../components/Loading.tsx";
import Pagination from "../components/Pagination.tsx";
import SectionTitle from "../components/SectionTitle.tsx";

const PLACEHOLDERS = ["Search by movie name or description...", "Try: Pinocchio...", "Try: A Fanciful Saga...", "Try: Clones...", "Try: A Insightful Saga...", "Try: Love...", "Try: Alien...",];

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

    const [animatedPlaceholder, setAnimatedPlaceholder] = useState("");
    const [placeholderIndex, setPlaceholderIndex] = useState(0);
    const [placeholderCharIndex, setPlaceholderCharIndex] = useState(0);
    const [isDeletingPlaceholder, setIsDeletingPlaceholder] = useState(false);

    useEffect(() => {
        if (inputValue.length > 0) {
            return;
        }

        const currentPhrase = PLACEHOLDERS[placeholderIndex];
        let timeout = 100;
        const base_timeout = 2300;

        if (!isDeletingPlaceholder && placeholderCharIndex < currentPhrase.length) {
            // start writing
            timeout = base_timeout / currentPhrase.length;
        } else if (!isDeletingPlaceholder && placeholderCharIndex === currentPhrase.length) {
            // stop writing
            timeout = base_timeout / 3.14;
        } else if (isDeletingPlaceholder && placeholderCharIndex > 0) {
            // start deleting
            timeout = base_timeout / (currentPhrase.length * 1.2);
        } else if (isDeletingPlaceholder && placeholderCharIndex === 0) {
            // stop deleting
            timeout = 100;
        }

        const timer = window.setTimeout(() => {
            if (!isDeletingPlaceholder) {
                if (placeholderCharIndex < currentPhrase.length) {
                    const nextCharIndex = placeholderCharIndex + 1;
                    setAnimatedPlaceholder(currentPhrase.slice(0, nextCharIndex));
                    setPlaceholderCharIndex(nextCharIndex);
                } else {
                    setIsDeletingPlaceholder(true);
                }
            } else {
                if (placeholderCharIndex > 0) {
                    const nextCharIndex = placeholderCharIndex - 1;
                    setAnimatedPlaceholder(currentPhrase.slice(0, nextCharIndex));
                    setPlaceholderCharIndex(nextCharIndex);
                } else {
                    setIsDeletingPlaceholder(false);
                    setPlaceholderIndex((prev) => (prev + 1) % PLACEHOLDERS.length);
                }
            }
        }, timeout);

        return () => {
            window.clearTimeout(timer);
        };
    }, [inputValue, placeholderIndex, placeholderCharIndex, isDeletingPlaceholder]);


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

    return (<div className="page">
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
                        placeholder={inputValue ? "" : animatedPlaceholder}
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

            {!trimmedInput && (<div className="panel">
                    Start typing to search films by title or description.
                </div>)}

            {loading && <Loading label="Searching films..."/>}

            {error && !loading && <ErrorMessage message={error}/>}

            {hasQuery && !loading && !error && (<>
                    <div className="panel">
                        Found {totalResults} result{totalResults === 1 ? "" : "s"} for{" "}
                        <strong>{debouncedQuery}</strong>
                    </div>

                    {data && data.content.length > 0 ? (<>
                            <div className="grid grid--cards">
                                {data.content.map((film) => (<FilmCard key={film.filmId} film={film}/>))}
                            </div>

                            <Pagination
                                page={data.number}
                                totalPages={data.totalPages}
                                onPrev={() => setPage((p) => Math.max(0, p - 1))}
                                onNext={() => setPage((p) => Math.min(data.totalPages - 1, p + 1))}
                            />
                        </>) : (<div className="panel">No films found.</div>)}
                </>)}
        </div>);
}