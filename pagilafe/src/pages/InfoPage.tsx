import {useEffect, useState} from "react";
import {api} from "../api/client";
import type {ApiInfoDto} from "../types/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import SectionTitle from "../components/SectionTitle";

export default function InfoPage() {
    const [info, setInfo] = useState<ApiInfoDto | null>(null);
    const [health, setHealth] = useState<ApiInfoDto | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        async function load() {
            try {
                setLoading(true);
                const [apiInfo, healthInfo] = await Promise.all([api.getApiInfo(), api.getHealth(),]);
                setInfo(apiInfo);
                setHealth(healthInfo);
            } catch (err) {
                setError(err instanceof Error ? err.message : "Unknown error");
            } finally {
                setLoading(false);
            }
        }

        load();
    }, []);

    if (loading) return <Loading/>;
    if (error) return <ErrorMessage message={error}/>;

    return (<div className="page">
            <SectionTitle
                title="Pagila APP "
                subtitle="A Spring Boot + PostgreSQL + React demo application."
            />

            <div className="panel panel-animated">
                <h2>Project Highlights</h2>
                <ul className="clean-list">
                    <li>Spring Boot REST API with DTO-based responses</li>

                    <li>
                        PostgreSQL data model based on the Pagila dataset by{" "}
                        <a className="link" href="https://github.com/devrimgunduz/pagila">devrimgunduz</a>,
                        containerized with Docker by me.
                    </li>

                    <li>React frontend with paginated browsing</li>

                    <li>
                        External API used:{" "}
                        <a href="https://api.dicebear.com" className="link">DiceBear</a> for generating avatar images
                    </li>

                    <li>Production-ready separation between backend and frontend</li>

                    <li>Click the footer to visit the GitHub repository.</li>
                </ul>
            </div>

            <div className="grid grid--2">
                <div className="panel panel-animated">
                    <h2>API Info</h2>
                    <p><strong>Name:</strong> {info?.name}</p>
                    <p><strong>Status:</strong> {info?.status}</p>
                    <p><strong>Version:</strong> {info?.version}</p>
                </div>

                <div className="panel panel-animated">
                    <h2>Health Check</h2>
                    <p><strong>Name:</strong> {health?.name}</p>
                    <p><strong>Status:</strong> {health?.status}</p>
                    <p><strong>Version:</strong> {health?.version}</p>
                </div>
            </div>

        </div>);
}