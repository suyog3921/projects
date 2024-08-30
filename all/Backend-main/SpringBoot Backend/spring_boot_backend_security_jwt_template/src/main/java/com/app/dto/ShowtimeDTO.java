package com.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShowtimeDTO {
    private Long id;
    private LocalTime showStartTime;
    private LocalTime showEndTime;
    private LocalDate showDate;
    private Set<SeatDTO> seats;

    // Constructors, getters, and setters
}