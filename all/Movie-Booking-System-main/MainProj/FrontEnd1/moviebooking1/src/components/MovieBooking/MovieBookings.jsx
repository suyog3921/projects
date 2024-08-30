import React, { useEffect, useState } from 'react';
import './MovieBooking.css';
import available from './seat_availabale.png'
import taken from './seat_booked.png'
import selected from './seat_selected.png'
import { paymentStart } from '../../Services/Payment';
import { useDispatch, useSelector } from 'react-redux';
import { increment, decrement, reset } from '../../Redux/cart/cartSlice';
import { useParams } from 'react-router-dom';
import { fetchMovieDetailsByid } from '../../Services/MovieList';
import config from '../../config';
import { availableSeats, bookSeat, findShowtime, updateSeats } from '../../Services/Showtimes';
import { toast } from 'react-toastify';

const MovieDetails = ({ movieData }) => {
  if (!movieData) {
    return <p>Loading movie details...</p>;
  }
  const rating = movieData.mrating;
  const maxStars = 5;
  const filledStars = Math.round((rating / 10) * maxStars);
  const emptyStars = maxStars - filledStars;

  return (
    <div className="movie-details">
      <img src={`${config.url}/moviestest/image/${movieData.id}`} alt={movieData.mname} />
      <h2>{movieData.mname}</h2>
      <div className="rating">
        {/* Render filled stars */}
        {Array(filledStars).fill('⭐️').map((star, index) => (
          <span key={`filled-star-${index}`}>{star}</span>
        ))}
        {/* Render empty stars */}
        {Array(emptyStars).fill(<>&#9734;</>).map((star, index) => (
          <span key={`empty-star-${index}`} style={{ color: '#ccc' }}>{star}</span>
        ))}
        <span>{`${rating} reviews`}</span>
      </div>
      <p>{movieData.mdescription}</p>
      <p>Rating: {movieData.mrating}</p>
    </div>
  );
};

const DateSelection = ({ selectedDate, onSelectDate }) => {
  const handleDateSelection = (index) => {
    const baseDate = new Date(2024, 4, 4); // Starting from 2024-05-04
    const selectedDate = new Date(baseDate);
    selectedDate.setDate(baseDate.getDate() + index);

    const formattedDate = selectedDate.toISOString().split('T')[0]; // Format date as YYYY-MM-DD
    onSelectDate(formattedDate);
  };

  return (
    <div className="date-selection">
      <ul>
        {['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'].map((day, index) => (
          <li
            key={index}
            className={selectedDate === new Date(2024, 4, 4 + index).toISOString().split('T')[0] ? 'selected' : ''}
            onClick={() => handleDateSelection(index)}
          >
            {day} <br /> {3 + index} May
          </li>
        ))}
      </ul>
    </div>
  );
};

const ShowTimeSelection = ({ selectedTime, onSelectTime }) => (
  <div className="showtime-selection">
    {['10:00-13:00', '13:00-16:00', '16:00-19:00', '19:00-22:00'].map((time, index) => (
      <button key={index} className={selectedTime === index ? 'selected' : ''} onClick={() => onSelectTime(index)}>
        {time}
      </button>
    ))}
  </div>
);


const SeatSelection = ({ seats, onSelectSeat }) => {
  const numCols = 5; // Fixed number of columns
  const numRows = Math.ceil(seats.length / numCols); // Calculate the number of rows based on total seats

  return (
    <div className="seat-selection">
      {Array.from({ length: numRows }, (_, rowIndex) => (
        <div key={rowIndex} className="seat-row">
          {Array.from({ length: numCols }, (_, colIndex) => {
            const seatIndex = rowIndex * numCols + colIndex; // Calculate the correct index in the 1D array
            if (seatIndex >= seats.length) {
              return null; // Skip rendering if seatIndex is out of bounds
            }
            const seat = seats[seatIndex];
            const seatImage = 
              seat.isSeatAvailable === true ? available : 
              seat.isSeatAvailable === false ? taken : 
              selected; // Handle `selected` separately if using it as a state

            return (
              <div
                key={seatIndex} // Unique key for each seat
                className="seat"
                onClick={() => onSelectSeat(seatIndex)} // Pass the correct seat index
              >
                <img src={seatImage} alt={seat.isSeatAvailable ? 'Available' : 'Taken'} />
              </div>
            );
          })}
        </div>
      ))}
    </div>
  );
};


const Summary = ({ selectedSeats, totalCost, onCheckout, seats }) => (
  <div className="summary">
    <p>Movies Selected: {seats}</p>
    <p>Total Cost: {totalCost} Rs</p>
    <button id='rzp-button1' onClick={() => onCheckout(totalCost)}>CHECKOUT</button>
  </div>
);

const MovieBookings = () => {
  const [selectedDate, setSelectedDate] = useState('2024-05-07'); // Default value in YYYY-MM-DD format
  const [selectedTime, setSelectedTime] = useState(3);
  const [showtimeId, setShowtimeId] = useState(null);
  const amount = useSelector((state) => state.cart.value);
  const tickets = useSelector((state) => state.cart.selectedSeats);
  const dispatch = useDispatch();
  const [movieData, setMovieData] = useState(null);
  const { id } = useParams();
  const [seats, setSeats] = useState(
    [{ type: 'regular', isSeatAvailable: 'available' }, { type: 'regular', isSeatAvailable: 'available' }, { type: 'regular', isSeatAvailable: 'taken' },
    { type: 'comfort', isSeatAvailable: 'taken' }, { type: 'comfort', isSeatAvailable: 'available' }, { type: 'comfort', isSeatAvailable: 'taken' },
    { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'available' },
    { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'taken' }, { type: 'vip', isSeatAvailable: 'available' },
    { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'taken' },
    { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'available' }, { type: 'vip', isSeatAvailable: 'taken' },
    { type: 'regular', isSeatAvailable: 'available' }, { type: 'regular', isSeatAvailable: 'available' },
  ]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchMovieDetailsByid(id);
        console.log('Fetched data:', data);
        setMovieData(data);
      } catch (error) {
        console.error("Error fetching movie details:", error);
      }
    };
    if (id) {
      fetchData();
    }
  }, [id]);

  useEffect(() => {
    if (movieData && selectedDate && selectedTime !== null) {
      const showTimes = [
        { start: '10:00', end: '13:00' },
        { start: '13:00', end: '16:00' },
        { start: '16:00', end: '19:00' },
        { start: '19:00', end: '22:00' }
      ];
  
      const { start: showStartTime, end: showEndTime } = showTimes[selectedTime];
      
      console.log('Requesting showtime with:', { selectedDate, showStartTime, showEndTime });
  
      findShowtime(selectedDate, showStartTime, showEndTime)
        .then( (showtime) => {
          console.log(showtime)
          setShowtimeId(showtime.data);
          console.log('Fetched Showtime ID:', showtime.data);
          return availableSeats(id, showtime.data);
        })
        .then((seats) => {
          setSeats(seats);
          console.log('Available Seats:', seats);
      })
        .catch((error) => {
          console.error('Error fetching showtime ID:', error);
        });
        dispatch(reset());

      
    }
  }, [selectedDate, selectedTime, movieData]);
  

  const handleSeatSelection = (seatIndex) => {
    const updatedSeats = [...seats];
    const seat = updatedSeats[seatIndex];
  
    if (seat.isSeatAvailable === true) {
      // Seat is available and being selected
      dispatch(increment());
      updatedSeats[seatIndex] = { ...seat, isSeatAvailable: 'selected' }; // Consider using a new property for selection state
    } else if (seat.isSeatAvailable === 'selected') {
      // Seat is selected and being deselected
      dispatch(decrement());
      updatedSeats[seatIndex] = { ...seat, isSeatAvailable: true }; // Set it back to available
    }
  
    setSeats(updatedSeats);
  };
  

  const handleCheckout = async (totalCost) => {
    try {
      const selectedSeats = seats.filter(seat => seat.isSeatAvailable === 'selected');
      // Start the payment process
      const userId = sessionStorage.getItem('userId')
      await paymentStart(totalCost);
      // After payment is successful, update seats
      toast.success("Your Seat has been booked!!")
      await bookSeat(selectedSeats, userId, movieData.id,showtimeId, totalCost)
      await updateSeats(selectedSeats);
      // Reset the cart
      dispatch(reset());
    } catch (error) {
      if(error.response.status===401)
        toast.error("Log in required!!!")
      else
        toast.error("Select tickets")
      console.error('Error during checkout:', error);
    }
  };

  return (
    <div className="movie-booking">
      <MovieDetails movieData={movieData} />
      <DateSelection selectedDate={selectedDate} onSelectDate={setSelectedDate} />
      <ShowTimeSelection selectedTime={selectedTime} onSelectTime={setSelectedTime} />
      <SeatSelection seats={seats} onSelectSeat={handleSeatSelection} />
      <Summary selectedSeats={seats} onCheckout={handleCheckout} totalCost={amount} seats={tickets} />
    </div>
  );
};

export default MovieBookings;


