import {useEffect, useState} from "react";
import {api} from "../api/client";
import type {CategoryDto, PageResponse} from "../types/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import Pagination from "../components/Pagination";
import SectionTitle from "../components/SectionTitle";

export default function CategoriesPage() {
    const [data, setData] = useState<PageResponse<CategoryDto> | null>(null);
    const [page, setPage] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const pageSize = 20

    useEffect(() => {
        async function load() {
            try {
                setLoading(true);
                const result = await api.getCategories(page, pageSize);
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

    return (
        <div className="page">
            <SectionTitle title="Categories" subtitle="Film categories exposed by the Spring Boot API."/>

            <div className="list-panel">
                {data.content.map((category: CategoryDto, index: number) => (
                    <div key={category.categoryId} className="list-row panel-animated">
                        <span>{page * pageSize + index + 1}</span>
                        <span>{category.name}</span>
                    </div>
                ))}
            </div>

            <Pagination
                page={data.number}
                totalPages={data.totalPages}
                onPrev={() => setPage((p) => Math.max(0, p - 1))}
                onNext={() => setPage((p) => Math.min(data.totalPages - 1, p + 1))}
            />
        </div>
    );
}