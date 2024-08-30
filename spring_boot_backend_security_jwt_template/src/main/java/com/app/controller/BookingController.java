package com.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BookingDTO;
import com.app.dto.BookingRequest;
import com.app.entities.BookingEntity;
import com.app.entities.BookingSeats;
import com.app.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping()
    public ResponseEntity<BookingEntity> bookSeats(@RequestBody BookingRequest bookingRequest) {
        Long userId = bookingRequest.getUserId();
        List<Long> seatId = bookingRequest.getSeatId();
        Long movieId = bookingRequest.getMovieId();
        Long showtimeId = bookingRequest.getShowtimeId();
        int totalPrice = bookingRequest.getTotalPrice();
        System.out.println(userId+" "+ seatId+" "+ movieId+" "+ showtimeId);
        BookingEntity booking = bookingService.bookSeats(userId, seatId, movieId, showtimeId, totalPrice);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        List<BookingEntity> bookings = bookingService.getBookingsByUserId(userId);

        List<BookingDTO> bookingDetails = bookings.stream().map(booking -> {
            List<BookingSeats> seats = bookingService.getSeatsByBookingId(booking.getId());

            List<Integer> seatNumbers = seats.stream()
                    .map((BookingSeats seat) -> seat.getSeat().getSeatNo())  // Explicit type declaration
                    .collect(Collectors.toList());

            return new BookingDTO(
                booking.getId(),
                booking.getUser().getFirstName() + " " + booking.getUser().getLastName(),
                booking.getShowtime().getMovie().getMName(),
                seatNumbers,
                booking.getShowtime().getShowStartTime()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(bookingDetails);
    }

}
