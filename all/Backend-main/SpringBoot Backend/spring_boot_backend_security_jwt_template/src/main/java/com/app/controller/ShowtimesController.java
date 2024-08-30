// ShowtimesController.java
package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.SeatDTO;
import com.app.dto.ShowtimeRequestDTO;
import com.app.entities.ShowtimesEntity;
import com.app.service.ShowtimesService;

@RestController
@RequestMapping("/api/showtimes")
@CrossOrigin(origins = "http://localhost:3000")
public class ShowtimesController {

    @Autowired
    private ShowtimesService showtimesService;

    @GetMapping("/{movieId}/{showtimeId}/seats")
    public ResponseEntity<List<SeatDTO>> getSeatsForShowtime(@PathVariable Long movieId, @PathVariable Long showtimeId) {
        List<SeatDTO> seats = showtimesService.getSeatsForShowtime(movieId, showtimeId);
        return ResponseEntity.ok(seats);
    }
    
    @PostMapping("/find")
    public ResponseEntity<Long> findShowtimeByCriteria(@RequestBody ShowtimeRequestDTO requestDTO) {
        Optional<ShowtimesEntity> showtime = showtimesService.getShowtimeByCriteria(requestDTO);
        return showtime.map(st -> ResponseEntity.ok(st.getId()))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
}




