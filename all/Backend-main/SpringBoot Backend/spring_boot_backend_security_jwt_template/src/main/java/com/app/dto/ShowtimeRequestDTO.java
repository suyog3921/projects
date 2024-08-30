package com.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeRequestDTO {
	 private LocalDate showDate;
	    private LocalTime showStartTime;
	    private LocalTime showEndTime;
}
