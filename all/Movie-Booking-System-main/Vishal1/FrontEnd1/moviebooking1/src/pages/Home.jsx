import Footer from "./Footer"
import Navbar from "./Navbar"

function Home(){
    return(
        <div>
            <Navbar />
            <main className="main-content">
        <section className="hero">
          <h1>Book your favorite movie tickets & enjoy the show!</h1>
          <button className="book-tickets-btn">Book tickets</button>
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
            <Footer/>
        </div>
    )
}

export default Home