import type {
    ActorDto, ApiInfoDto, CategoryDto, FilmDetailDto, FilmSummaryDto, PageResponse, CreateFilmRequestDto,
} from "../types/api";

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080";

async function apiFetch<T>(path: string, signal?: AbortSignal): Promise<T> {
    const response = await fetch(`${API_BASE}${path}`, {
        headers: {
            "Content-Type": "application/json",
        }, signal,
    });

    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || `Request failed with status ${response.status}`);
    }

    return response.json();
}

export const api = {
    getApiInfo: () => apiFetch<ApiInfoDto>("/api"),
    getHealth: () => apiFetch<ApiInfoDto>("/api/health"),

    getFilms: (page = 0, size = 12) => apiFetch<PageResponse<FilmSummaryDto>>(`/api/films?page=${page}&size=${size}`),

    getFilmById: (id: number) => apiFetch<FilmDetailDto>(`/api/films/${id}/detail`),

    getActors: (page = 0, size = 20) => apiFetch<PageResponse<ActorDto>>(`/api/actors?page=${page}&size=${size}`),

    getCategories: (page = 0, size = 20) => apiFetch<PageResponse<CategoryDto>>(`/api/categories?page=${page}&size=${size}`),

    searchFilms: (query: string, page = 0, size = 12, signal?: AbortSignal) => apiFetch<PageResponse<FilmSummaryDto>>(`/api/films/search?q=${encodeURIComponent(query)}&page=${page}&size=${size}`, signal),
    createFilm: async (payload: CreateFilmRequestDto) => {
        const response = await fetch(`${API_BASE}/api/films/add`, {
            method: "POST", headers: {
                "Content-Type": "application/json",
            }, body: JSON.stringify(payload),
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || `Request failed with status ${response.status}`);
        }

        return response.json() as Promise<FilmSummaryDto>;
    },
};