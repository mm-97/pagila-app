import {BrowserRouter, Route, Routes} from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import HomePage from "./pages/HomePage";
import FilmsPage from "./pages/FilmsPage";
import FilmDetailPage from "./pages/FilmDetailPage";
import ActorsPage from "./pages/ActorsPage";
import CategoriesPage from "./pages/CategoriesPage";
import SearchPage from "./components/SearchPage.tsx";

export default function App() {
    return (
        <BrowserRouter>
            <div className="app-shell">
                <Header/>
                <main className="container main-content">
                    <Routes>
                        <Route path="/" element={<HomePage/>}/>
                        <Route path="/films" element={<FilmsPage/>}/>
                        <Route path="/films/:id" element={<FilmDetailPage/>}/>
                        <Route path="/actors" element={<ActorsPage/>}/>
                        <Route path="/categories" element={<CategoriesPage/>}/>
                        <Route path="/search" element={<SearchPage/>}/>
                    </Routes>
                </main>
                <Footer/>
            </div>
        </BrowserRouter>
    );
}