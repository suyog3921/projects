package com.app.service;

import com.app.dto.SeatDTO; 
import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;
import com.app.repository.ShowtimesRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.ShowtimeRequestDTO;

@Service
@Transactional
public class ShowtimesServiceImpl implements ShowtimesService {

    @Autowired
    private ShowtimesRepository showtimesRepository;

//    @Transactional
//    public List<SeatEntity> getSeatsForShowtime(Long movieId, Long showtimeId) {
//        Optional<ShowtimesEntity> showtimeOpt = showtimesRepository.findByMovieIdAndShowtimeId(movieId, showtimeId);
//        
//        if (showtimeOpt.isPresent()) {
//            ShowtimesEntity showtime = showtimeOpt.get();
//            Hibernate.initialize(showtime.getSeats()); // Initialize the seats collection
//            return List.copyOf(showtime.getSeats());
//        } else {
//            throw new RuntimeException("Showtime not found for the provided movieId and showtimeId");
//        }
//    }
    
    //@Transactional
    public List<SeatDTO> getSeatsForShowtime(Long movieId, Long showtimeId) {
        return showtimesRepository.findSeatsByMovieIdAndShowtimeId(movieId, showtimeId);
    }
    
    public Optional<ShowtimesEntity> getShowtimeByCriteria(ShowtimeRequestDTO requestDTO) {
        return showtimesRepository.findByShowDateAndShowStartTimeAndShowEndTime(
                requestDTO.getShowDate(), 
                requestDTO.getShowStartTime(), 
                requestDTO.getShowEndTime()
        );
    }
}


