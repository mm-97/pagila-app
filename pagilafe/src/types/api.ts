export type ApiInfoDto = {
    name: string;
    status: string;
    version: string;
};

export type ActorDto = {
    actorId: number;
    firstName: string;
    lastName: string;
};

export type CategoryDto = {
    categoryId: number;
    name: string;
};

export type FilmSummaryDto = {
    filmId: number;
    title: string;
    description: string;
    rating: string;
    length: number | null;
    rentalRate: number;
};

export type FilmDetailDto = {
    filmId: number;
    title: string;
    description: string;
    releaseYear: unknown;
    rentalDuration: number;
    rentalRate: number;
    length: number | null;
    replacementCost: number;
    rating: string;
    actors: ActorDto[];
    categories: CategoryDto[];
};

export type PageResponse<T> = {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    first: boolean;
    last: boolean;
    numberOfElements: number;
    empty: boolean;
};

export type CreateFilmRequestDto = {
        title: string;
        description: string;
        releaseYear: number | null;
        rentalDuration: number;
        rentalRate: number;
        length: number | null;
        replacementCost: number;
        rating: string;
        actorIds: number[];
        categoryIds: number[];
}