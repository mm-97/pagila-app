import {useState} from "react";

export default function Footer() {
    const [isHovering, setIsHovering] = useState(false);
    const [mousePosition, setMousePosition] = useState({x: 0, y: 0});

    return (<>
            <a
                href="https://github.com/mm-97"
                target="_blank"
                rel="noopener noreferrer"
                className="footer footer-link-area"
                onMouseEnter={() => setIsHovering(true)}
                onMouseLeave={() => setIsHovering(false)}
                onMouseMove={(e) => setMousePosition({
                    x: e.clientX, y: e.clientY - 70,
                })}
            >
                <div className="container footer-content">
          <span className="footer-text">
            Made with <span className="heart">♥</span> by
          </span>

                    <span className="github-link">
            <svg
                className="github-icon"
                viewBox="0 0 24 24"
                aria-hidden="true"
            >
              <path
                  fill="currentColor"
                  d="M12 0C5.37 0 0 5.37 0 12
                c0 5.3 3.44 9.8 8.2 11.39
                .6.11.82-.26.82-.58
                0-.29-.01-1.07-.02-2.1
                -3.34.73-4.04-1.61-4.04-1.61
                -.55-1.38-1.34-1.75-1.34-1.75
                -1.1-.75.08-.73.08-.73
                1.21.09 1.85 1.24 1.85 1.24
                1.08 1.84 2.83 1.31 3.52 1
                .11-.78.42-1.31.76-1.61
                -2.66-.3-5.47-1.33-5.47-5.93
                0-1.31.47-2.38 1.24-3.22
                -.13-.3-.54-1.52.12-3.17
                0 0 1.01-.32 3.3 1.23
                .96-.27 1.98-.4 3-.4
                1.02 0 2.04.13 3 .4
                2.28-1.55 3.29-1.23 3.29-1.23
                .66 1.65.25 2.87.12 3.17
                .77.84 1.23 1.91 1.23 3.22
                0 4.61-2.81 5.63-5.49 5.92
                .43.37.81 1.1.81 2.22
                0 1.6-.01 2.89-.01 3.29
                0 .32.21.7.82.58
                C20.57 21.8 24 17.3 24 12
                24 5.37 18.63 0 12 0z"
              />
            </svg>

            <span>mm-97</span>
            </span>
                </div>
            </a>

            {isHovering && (<div
                    className="footer-tooltip"
                    style={{
                        left: mousePosition.x + 16, top: mousePosition.y + 16,
                    }}
                >
                    Click, don’t be afraid :)
                </div>)}
        </>);
}