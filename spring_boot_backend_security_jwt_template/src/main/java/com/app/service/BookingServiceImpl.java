package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.BookingEntity;
import com.app.entities.BookingSeats;
import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;
import com.app.entities.UserEntity;
import com.app.repository.BookingRepository;
import com.app.repository.BookingSeatsRepository;
import com.app.repository.SeatRepository;
import com.app.repository.ShowtimesRepository;
import com.app.repository.UserEntityRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingSeatsRepository bookingSeatsRepository;
    @Autowired
    private SeatRepository seatRepository;
	@Autowired
	private EmailSenderService senderService;
    @Autowired
    private ShowtimesRepository showtimesRepository;

    @Autowired
    private UserEntityRepository userRepository;
    
    public void sendBookingConfirmation(UserEntity user, BookingEntity booking, List<SeatEntity> seats) {
        String email = user.getEmail();
        String subject = "Your MovieMagic Booking Confirmation!";
        
        // Formatting the seat numbers as a comma-separated string
        String seatNumbers = seats.stream()
                                  .map(seat -> String.valueOf(seat.getSeatNo()))
                                  .collect(Collectors.joining(", "));
        
        String body = String.format(
            "<html>" +
            "<body style='font-family: Arial, sans-serif; color: #333;'>" +
            "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
            "<h2 style='color: #2a2a2a;'>Thank you for your booking, %s %s!</h2>" +
            "<p style='font-size: 16px;'>We are excited to confirm your booking with <strong>MovieMagic</strong>.</p>" +
            "<p style='font-size: 16px;'>Here are your booking details:</p>" +
            "<ul style='font-size: 16px;'>" +
            "<li><strong>Booking ID:</strong> %d</li>" +
            "<li><strong>Transaction ID:</strong> %d</li>" +
            "<li><strong>Movie Name:</strong> %s</li>" +
            "<li><strong>Showtime:</strong> %s</li>" +
            "<li><strong>Seat Numbers:</strong> %s</li>" +
            "</ul>" +
            "<p style='font-size: 16px;'>Please arrive 15 minutes before the showtime. We hope you have a fantastic movie experience!</p>" +
            "<p style='font-size: 16px;'>If you have any questions, feel free to contact our support team. We’re here to help you!</p>" +
            "<p style='font-size: 16px;'>Best regards,<br/>The MovieMagic Team</p>" +
            "<footer style='margin-top: 20px; padding-top: 10px; border-top: 1px solid #ddd; font-size: 12px; color: #888;'>MovieMagic Inc., Pune <br/></footer>" +
            "</div>" +
            "</body>" +
            "</html>",
            user.getFirstName(),
            user.getLastName(),
            booking.getId(),
            booking.getTransactionId(),  // Assuming there's a method getTransactionId() or a way to retrieve it
            booking.getShowtime().getMovie().getMName(),
            booking.getShowtime().getFormattedShowtime(),  // Assuming you have a method to format showtime details
            seatNumbers
        );

        senderService.sendHtmlEmail(email, subject, body);
    }

    @Override
    public BookingEntity bookSeats(Long userId, List<Long> id, Long movieId, Long showtimeId, int totalPrice) {
        // Fetch the user
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        UserEntity user = userOpt.get();

        // Fetch the showtime and its seats
        Optional<ShowtimesEntity> showtimeOpt = showtimesRepository.findById(showtimeId);
        if (!showtimeOpt.isPresent() || !showtimeOpt.get().getMovie().getId().equals(movieId)) {
            throw new RuntimeException("Showtime or movie not found");
        }
        ShowtimesEntity showtime = showtimeOpt.get();

        // Fetch and validate seats
        List<SeatEntity> seats = seatRepository.findByIdInAndShowtime(id, showtime);
        if (seats.size() != id.size()) {
            throw new RuntimeException("Some seats not found");
        }

        // Check availability
        for (SeatEntity seat : seats) {
            if (!seat.getIsSeatAvailable()) {
                throw new RuntimeException("One or more seats are already booked");
            }
            seat.setIsSeatAvailable(false);
        }
        seatRepository.saveAll(seats);

        // Create booking
        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setBookingDate(LocalDateTime.now()); // Set the booking date to the current time
        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);
        
     // Create booking-seats relation
        for (SeatEntity seat : seats) {
            BookingSeats bookingSeat = new BookingSeats();
            bookingSeat.setBooking(booking);
            bookingSeat.setSeat(seat);
            bookingSeatsRepository.save(bookingSeat);
        }
        sendBookingConfirmation(user,booking,seats);
        return booking;
    }
    @Transactional
    public List<BookingEntity> getBookingsByUserId(Long userId) {
    	List<BookingEntity> bookings = bookingRepository.findByUserId(userId);
        // Initialize lazy-loaded entities
        bookings.forEach(booking -> {
            booking.getUser().getFirstName();  // Force initialization
            booking.getShowtime().getMovie().getMName();  // Force initialization
        });
        return bookings;
    }
    @Transactional
    public List<BookingSeats> getSeatsByBookingId(Long bookingId) {
        return bookingSeatsRepository.findByBookingId(bookingId);
    }
}
