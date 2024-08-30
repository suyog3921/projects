package com.app.service;

import java.util.List;

import com.app.entities.BookingEntity;
import com.app.entities.BookingSeats;

public interface BookingService {
	public List<BookingSeats> getSeatsByBookingId(Long bookingId);
	public List<BookingEntity> getBookingsByUserId(Long userId);
	public BookingEntity bookSeats(Long userId, List<Long> id, Long movieId, Long showtimeId, int totalPrice);
}
