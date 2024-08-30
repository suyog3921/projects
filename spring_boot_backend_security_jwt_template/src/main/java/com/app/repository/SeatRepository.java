package com.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
//    List<SeatEntity> findAllById(List<Long> ids);
	 // Find seats by showtime
    List<SeatEntity> findByShowtime(ShowtimesEntity showtime);

    // Find a seat by its seatNo and showtime
    SeatEntity findBySeatNoAndShowtime(int seatNo, ShowtimesEntity showtime);

    List<SeatEntity> findByIdInAndShowtime(List<Long> id, ShowtimesEntity showtime);
    
    List<SeatEntity> findBySeatNoInAndShowtime(List<Integer> seatNos, ShowtimesEntity showtime);
    @Modifying
    @Transactional
    @Query("UPDATE SeatEntity s SET s.isSeatAvailable = :isSeatAvailable WHERE s.id = :id")
	void updateSeatAvailability(Long id, Boolean isSeatAvailable);

	
}