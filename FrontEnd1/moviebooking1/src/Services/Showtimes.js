import axios from "axios";
import config from "../config";

export async function availableSeats(movieId , showtimeId){     
    try{
        const response = await axios.get(`${config.url}/api/showtimes/${movieId}/${showtimeId}/seats`);
        return response.data;
    }catch(error){
        console.log('error-response', error);
        // toast.error(error.response.data.message)
    }
}

export async function fetchMovieDetailsByid(id){     
    try{
        const response = await axios.get(`${config.url}/moviestest/${id}`);
        return response.data;
    }catch(error){
        console.log('error-response', error);
        // toast.error(error.response.data.message)
    }
}

export async function findShowtime(showDate, showStartTime, showEndTime) {
    try {
        console.log(showDate, showStartTime, showEndTime)
        const formatTime = time => time.length === 5 ? time : `${time}:00`;
        const response = await axios.post(`${config.url}/api/showtimes/find`, {
            showDate,
            showStartTime: formatTime(showStartTime),
            showEndTime: formatTime(showEndTime)
        });
        console.log('Showtime ID:', response.data);
        return response

    } catch (error) {
        console.error('Error finding showtime:', error);
    }
};


export async function updateSeats(selectedSeats) {
    try {
      const response = await axios.post(`${config.url}/seat/update-seats`, {
        seats: selectedSeats.map(seat => ({
          id: seat.id,
          isSeatAvailable: false // Mark as taken
        }))
      });
      return response.data;
    } catch (error) {
      console.error('Error updating seats:', error);
      throw error;
    }
  };

  export async function bookSeat(selectedSeats, userId, movieId, showtimeId, totalPrice){
    try {
        const token = sessionStorage.getItem('token')
        const numericUserId = Number(userId);
        const id = selectedSeats.map(seat => seat.id);
        const response = await axios.post(`${config.url}/api/bookings`, {
            userId : numericUserId,
            id,
            movieId,
            showtimeId,
            totalPrice
        },
        {
            headers: {
                Authorization: token // Include the token in the Authorization header
            }
        }
    );
        console.log(response)
        return response.data
    } catch (error) {
        console.error('Error Booking seats:', error);
    }
  }

// export async function fetchSliderDetails(firstName, lastName, email, password){
//     const body = {
//         firstName, lastName, email, password
//     }
//     const response = await axios.post(`${config.url}/users/signup`, body)
//     return response.data
// }