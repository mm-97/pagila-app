import {Link, NavLink} from "react-router-dom";

export default function Header() {
    return (
        <header className="header">
            <div className="container header__inner">
                <Link to="/" className="brand">
                    PagilaLab
                </Link>

                <nav className="nav">
                    <NavLink to="/" className="nav__link">
                        Home
                    </NavLink>
                    <NavLink to="/films" className="nav__link">
                        Films
                    </NavLink>
                    <NavLink to="/actors" className="nav__link">
                        Actors
                    </NavLink>
                    <NavLink to="/categories" className="nav__link">
                        Categories
                    </NavLink>
                    <NavLink to="/search" className="nav__link">
                        Search
                    </NavLink>
                </nav>
            </div>
        </header>
    );
}