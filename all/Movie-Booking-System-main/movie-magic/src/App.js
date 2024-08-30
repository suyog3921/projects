import logo from "./logo.svg";
import "./App.css";
import { useState } from "react";
import { Header } from "./components/header/header";
import logoPage from "./file.png";

function App() {
  return (
    <div className="app">
      <Header />
      <main className="main-content">
        <section className="hero">
          <div className="hero-content">
            <div className="hero-text">
              <h1>Book your favorite movie tickets & enjoy the show!</h1>
              <button className="book-tickets-btn">Book tickets</button>
            </div>
            <div className="hero-image">
              <img src={logoPage} alt="Hero" />
            </div>
          </div>
        </section>

        <section className="popular-movies">
          <h2>Find Popular Movies</h2>
          <div className="movies-grid">
            <div className="movie-card">Movie Magic Trio</div>
            <div className="movie-card">Comedy Showtime</div>
            <div className="movie-card">Laugh Out Loud</div>
            <div className="movie-card">All Genres Welcome</div>
            <div className="movie-card">Now Showing!</div>
          </div>
        </section>

        <section className="movie-categories">
          <h2>Movie Categories</h2>
          <div className="categories-grid">
            <span>Action</span>
            <span>Romance</span>
            <span>Thriller</span>
            <span>Fantasy</span>
            <span>Sci-fi</span>
            <span>Comedy</span>
            <span>Adventure</span>
            <span>Animation</span>
            <span>Family</span>
            <span>Horror</span>
            <span>Documentary</span>
            <span>Musical</span>
            <span>Biography</span>
            <span>War</span>
            <span>Western</span>
            <span>Superhero</span>
            <span>Classic</span>
          </div>
        </section>

        <section className="ticket-plans">
          <h2>Select Your Ticket Plan</h2>
          <div className="plans-grid">
            <div className="plan-card">
              <h3>Basic</h3>
              <p>FREE</p>
              <ul>
                <li>Up to 3 movies</li>
                <li>2 bookings monthly</li>
                <li>7 days trial</li>
              </ul>
              <button>Start for Free</button>
            </div>
            <div className="plan-card">
              <h3>Premium</h3>
              <p>$80</p>
              <ul>
                <li>Unlimited movies</li>
                <li>Unlimited bookings</li>
                <li>7 days trial</li>
              </ul>
              <button>Start with Premium</button>
            </div>
          </div>
        </section>
      </main>

      <footer className="footer">
        <p>MovieMagic - Your go-to movie ticket platform since 2020</p>
        <div className="footer-links">
          <a href="#">Terms of service</a>
          <a href="#">Contact support</a>
          <a href="#">FAQs</a>
          <a href="#">Get in touch</a>
        </div>
      </footer>
    </div>
  );
}

export default App;
