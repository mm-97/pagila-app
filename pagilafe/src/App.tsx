import {BrowserRouter, Route, Routes} from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import InfoPage from "./pages/InfoPage.tsx";
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
                        <Route path="/" element={<FilmsPage/>}/>
                        <Route path="/films" element={<FilmsPage/>}/>
                        <Route path="/films/:id" element={<FilmDetailPage/>}/>
                        <Route path="/actors" element={<ActorsPage/>}/>
                        <Route path="/categories" element={<CategoriesPage/>}/>
                        <Route path="/search" element={<SearchPage/>}/>
                        <Route path="/info" element={<InfoPage/>}/>
                    </Routes>
                </main>
                <Footer/>
            </div>
        </BrowserRouter>
    );
}