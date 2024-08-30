import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BookingHistory.css';
import config from '../../config';

const BookingHistory = () => {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const userId = sessionStorage.getItem('userId')
    const token = sessionStorage.getItem('token')
    const fetchBookings = async () => {
      try {
        const response = await axios.get(`${config.url}/api/bookings/user/${userId}`,{
          headers: {
            Authorization: token // Include the token in the Authorization header
        }
        });
        setBookings(response.data);
      } catch (error) {
        console.error('Error fetching bookings:', error);
      }
    };

    fetchBookings();
  }, []);

  return (
    <div className="booking-history-container">
      <h2 className="page-header">Booking History</h2>
      
      {bookings.length === 0 ? (
        <p>No bookings found.</p>
      ) : (
        bookings.map((booking) => (
          <div key={booking.bookingId} className="booking-card">
            <div className="booking-info">
              <h3>{booking.movieName}</h3>
              <p><strong>User:</strong> {booking.userName}</p>
              <p><strong>Showtime:</strong> {booking.showtime}</p>
              <p><strong>Booking Date:</strong> {booking.bookingDate}</p>
              <p><strong>Seats:</strong> {booking.seats.length > 0 ? booking.seats.join(', ') : 'No seats selected'}</p>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default BookingHistory;
