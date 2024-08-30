// ShowtimesRepository.java
package com.app.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.dto.SeatDTO;
import com.app.entities.ShowtimesEntity;

@Repository
public interface ShowtimesRepository extends JpaRepository<ShowtimesEntity, Long> {

    @Query("SELECT s FROM ShowtimesEntity s WHERE s.movie.id = :movieId AND s.id = :showtimeId")
    Optional<ShowtimesEntity> findByMovieIdAndShowtimeId(Long movieId, Long showtimeId);
    
    @Query("SELECT new com.app.dto.SeatDTO(s.id, s.seatNo, s.isSeatAvailable) FROM SeatEntity s WHERE s.showtime.movie.id = :movieId AND s.showtime.id = :showtimeId")
    List<SeatDTO> findSeatsByMovieIdAndShowtimeId(Long movieId, Long showtimeId);
    
    Optional<ShowtimesEntity> findByShowDateAndShowStartTimeAndShowEndTime(LocalDate showDate, LocalTime showStartTime, LocalTime showEndTime);

}
