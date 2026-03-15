import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {api} from "../api/client";
import type {ActorDto, CategoryDto, CreateFilmRequestDto} from "../types/api";
import SectionTitle from "../components/SectionTitle";
import ErrorMessage from "../components/ErrorMessage";
import Loading from "../components/Loading";
import SelectableList from "../components/SelectableList.tsx";

type FilmFormState = {
    title: string;
    description: string;
    releaseYear: string;
    rentalDuration: string;
    rentalRate: string;
    length: string;
    replacementCost: string;
    rating: string;
};

const INITIAL_FORM: FilmFormState = {
    title: "",
    description: "",
    releaseYear: "1990",
    rentalDuration: "7",
    rentalRate: "4.99",
    length: "60",
    replacementCost: "19.99",
    rating: "PG",
};

const RATING_OPTIONS = ["G", "PG", "PG-13", "R", "NC-17"];

export default function AddFilmPage() {
    const navigate = useNavigate();

    const [form, setForm] = useState<FilmFormState>({...INITIAL_FORM});

    const [actors, setActors] = useState<ActorDto[]>([]);
    const [categories, setCategories] = useState<CategoryDto[]>([]);
    const [selectedActorIds, setSelectedActorIds] = useState<number[]>([]);
    const [selectedCategoryIds, setSelectedCategoryIds] = useState<number[]>([]);

    const [loadingOptions, setLoadingOptions] = useState(true);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    useEffect(() => {
        async function loadOptions() {
            try {
                setLoadingOptions(true);
                setError("");

                const [actorsResponse, categoriesResponse] = await Promise.all([api.getActors(0, 500), api.getCategories(0, 200),]);

                setActors(actorsResponse.content);
                setCategories(categoriesResponse.content);
            } catch (err) {
                setError(err instanceof Error ? err.message : "Unable to load actors and categories");
            } finally {
                setLoadingOptions(false);
            }
        }

        loadOptions();
    }, []);

    function updateField<K extends keyof FilmFormState>(key: K, value: FilmFormState[K]) {
        setForm((prev) => ({...prev, [key]: value}));
    }

    function toggleActor(actorId: number, checked: boolean) {
        setSelectedActorIds((prev) => {
            if (checked) {
                return prev.includes(actorId) ? prev : [...prev, actorId];
            }
            return prev.filter((id) => id !== actorId);
        });
    }

    function toggleCategory(categoryId: number, checked: boolean) {
        setSelectedCategoryIds((prev) => {
            if (checked) {
                return prev.includes(categoryId) ? prev : [...prev, categoryId];
            }
            return prev.filter((id) => id !== categoryId);
        });
    }

    async function handleSubmit() {
        try {
            setLoading(true);
            setError("");
            setSuccessMessage("");

            const payload: CreateFilmRequestDto = {
                title: form.title.trim(),
                description: form.description.trim(),
                releaseYear: form.releaseYear ? Number(form.releaseYear) : null,
                rentalDuration: Number(form.rentalDuration),
                rentalRate: Number(form.rentalRate),
                length: form.length ? Number(form.length) : null,
                replacementCost: Number(form.replacementCost),
                rating: form.rating.trim(),
                actorIds: selectedActorIds,
                categoryIds: selectedCategoryIds,
            };

            const createdFilm = await api.createFilm(payload);

            setSuccessMessage(`Film created successfully: ${createdFilm.title}`);
            setForm({...INITIAL_FORM});
            setSelectedActorIds([]);
            setSelectedCategoryIds([]);

            window.setTimeout(() => {
                navigate(`/films/${createdFilm.filmId}`);
            }, 800);
        } catch (err) {
            setError(err instanceof Error ? err.message : "Unknown error");
        } finally {
            setLoading(false);
        }
    }

    return (<div className="page">
        <SectionTitle
            title="Add Film"
            subtitle="Create a new film and link actors and categories through a multi-select form."
        />

        <div className="panel panel-animated form-panel">
            <div className="form-grid">
                <label htmlFor={"search-input"} className="form-field">
                    <span>Title *</span>
                    <input
                        value={form.title}
                        onChange={(e) => updateField("title", e.target.value)}
                        className="search-input"
                    />
                </label>

                <label className="form-field">
                    <span>Rating</span>
                    <select
                        value={form.rating}
                        onChange={(e) => updateField("rating", e.target.value)}
                        className="form-select"
                    >
                        {RATING_OPTIONS.map((rating) => (<option key={rating} value={rating}>
                            {rating}
                        </option>))}
                    </select>
                </label>

                <label className="form-field form-field--full">
                    <span>Description</span>
                    <textarea
                        value={form.description}
                        onChange={(e) => updateField("description", e.target.value)}
                        className="search-input form-textarea"
                    />
                </label>

                <label className="form-field">
                    <span>Release Year</span>
                    <input
                        type="number"
                        value={form.releaseYear}
                        onChange={(e) => updateField("releaseYear", e.target.value)}
                        className="search-input"
                    />
                </label>

                <label className="form-field">
                    <span>Rental Duration *</span>
                    <input
                        type="number"
                        value={form.rentalDuration}
                        onChange={(e) => updateField("rentalDuration", e.target.value)}
                        className="search-input"
                    />
                </label>

                <label className="form-field">
                    <span>Rental Rate *</span>
                    <input
                        type="number"
                        step="0.01"
                        value={form.rentalRate}
                        onChange={(e) => updateField("rentalRate", e.target.value)}
                        className="search-input"
                    />
                </label>

                <label className="form-field">
                    <span>Length</span>
                    <input
                        type="number"
                        value={form.length}
                        onChange={(e) => updateField("length", e.target.value)}
                        className="search-input"
                    />
                </label>

                <label className="form-field">
                    <span>Replacement Cost *</span>
                    <input
                        type="number"
                        step="0.01"
                        value={form.replacementCost}
                        onChange={(e) => updateField("replacementCost", e.target.value)}
                        className="search-input"
                    />
                </label>

                <SelectableList
                    title="Actors"
                    loading={loadingOptions}
                    items={actors}
                    selectedIds={selectedActorIds}
                    getId={(actor) => actor.actorId}
                    getLabel={(actor) => `${actor.firstName} ${actor.lastName}`}
                    onToggle={toggleActor}
                    helpText="Select one or more actors."
                    loadingText="Loading actors..."
                />

                <SelectableList
                    title="Categories"
                    loading={loadingOptions}
                    items={categories}
                    selectedIds={selectedCategoryIds}
                    getId={(category) => category.categoryId}
                    getLabel={(category) => category.name}
                    onToggle={toggleCategory}
                    helpText="Select one or more categories."
                    loadingText="Loading categories..."
                />
            </div>

            <div className="form-actions">
                <button
                    type="button"
                    onClick={handleSubmit}
                    disabled={loading || loadingOptions || !form.title.trim()}
                >
                    Create Film
                </button>
            </div>
        </div>

        {loading && <Loading label="Creating film..."/>}
        {error && <ErrorMessage message={error}/>}
        {successMessage && <div className="panel">{successMessage}</div>}
    </div>);
}