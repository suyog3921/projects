import Navbar from "../components/navbar/Navbar"
import Footer from "../components/footer/Footer"
import MovieBookings from "../components/MovieBooking/MovieBookings"
function MovieBooking(){
    return(
        <div>
            <Navbar/>
            <h2 className="page-header">MovieBooking</h2>
            <MovieBookings/>
            <Footer/>
        </div>
    )
}

export default MovieBooking