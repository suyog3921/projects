package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookingDTO {
    private Long bookingId;
    private String userName;
    private String movieName;
    private List<Integer> seats;
    private LocalTime showtime;
}
