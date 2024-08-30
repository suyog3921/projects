import React, { useState } from "react";
import "./header.css";

export const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div>
      <header className="header">
        <nav className="navbar">
          <div className="logo">MovieMagic</div>
          <div className={`nav-links ${isMenuOpen ? "open" : ""}`}>
            <ul>
              <li>Home</li>
              <li>Movies</li>
              <li>Theaters</li>
              <li>Showtimes</li>
            </ul>
          </div>
          <div className="auth-buttons">
            <button className="login-btn">Log in</button>
            <button className="signup-btn">Sign up</button>
          </div>
          <div className="hamburger-menu" onClick={toggleMenu}>
            <div className={`bar ${isMenuOpen ? "change" : ""}`}></div>
            <div className={`bar ${isMenuOpen ? "change" : ""}`}></div>
            <div className={`bar ${isMenuOpen ? "change" : ""}`}></div>
          </div>
        </nav>
      </header>
    </div>
  );
};
