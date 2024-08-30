package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.SeatDTO;
import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private com.app.repository.SeatRepository seatRepository;

    // Get all seats for a specific showtime
    public List<SeatEntity> getSeatsByShowtime(ShowtimesEntity showtime) {
        return seatRepository.findByShowtime(showtime);
    }

    // Get a seat by seatNo and showtime
    public List<SeatEntity> getSeatByIdAndShowtime(List<Long> id, ShowtimesEntity showtime) {
        return seatRepository.findByIdInAndShowtime(id, showtime);
    }
    
    //Update seats
    public void updateSeats(List<SeatDTO> seats) {
        for (SeatDTO seat : seats) {
            seatRepository.updateSeatAvailability(seat.getId(), seat.getIsSeatAvailable());
        }
    }
}