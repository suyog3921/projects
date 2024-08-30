import Navbar from "../components/navbar/Navbar"
import Footer from "../components/footer/Footer"
import BookingHistory from "../components/History/BookingHistory"
function TheaterList(){
    return(
        <div>
            <Navbar/>
            {/* <h2 className="page-header">My Tickets</h2> */}
            <BookingHistory/>
            <Footer/>
        </div>
    )
}

export default TheaterList