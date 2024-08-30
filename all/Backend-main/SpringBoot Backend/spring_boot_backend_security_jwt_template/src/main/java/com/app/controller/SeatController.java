package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.SeatUpdateRequest;
import com.app.service.SeatServiceImpl;

@RestController
@RequestMapping("/seat")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatController {

    @Autowired
    private SeatServiceImpl seatService;
// updating seats to database
    @PostMapping("/update-seats")
    public ResponseEntity<Void> updateSeats(@RequestBody SeatUpdateRequest seatUpdateRequest) {
        seatService.updateSeats(seatUpdateRequest.getSeats());
        return ResponseEntity.ok().build();
    }
}
