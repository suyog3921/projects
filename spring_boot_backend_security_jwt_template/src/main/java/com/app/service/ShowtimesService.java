package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.dto.SeatDTO;
import com.app.dto.ShowtimeRequestDTO;
import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;

public interface ShowtimesService {

	public List<SeatDTO> getSeatsForShowtime(Long movieId, Long showtimeId);

	public Optional<ShowtimesEntity> getShowtimeByCriteria(ShowtimeRequestDTO requestDTO);
}
